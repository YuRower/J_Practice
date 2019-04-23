package com.nixsolutions.controller;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nixsolutions.configuration.AppConfig;
import com.nixsolutions.model.Role;
import com.nixsolutions.model.User;
import com.nixsolutions.service.CaptchaService;
import com.nixsolutions.service.UserProfileService;
import com.nixsolutions.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
public class AppControllerTest {

	@Mock
	UserService userService;
	@Mock
	UserProfileService userProfileService;
	@Mock
	MessageSource messageSource;
	@Mock
	private PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;
	@Mock
	private AuthenticationTrustResolver authenticationTrustResolver;
	@Mock
	CaptchaService captcha;

	private MockMvc mockMvc;

	@InjectMocks
	private AppController appController;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(appController).build();
	}

	@WithMockUser(roles = "ADMIN")
	@Test
	public void testNewUser() throws Exception {
		mockMvc.perform(get("/newuser")).andExpect(status().isOk())
				.andExpect(model().attribute("user", instanceOf(User.class)))
				.andExpect(model().attribute("edit", false)).andExpect(model().attribute("action_user", "Creation"))
				.andExpect(view().name("registration"));
	}

	@WithMockUser(roles = "ADMIN")
	@Test
	public void testEditUser() throws Exception {
		when(userService.findByLogin(anyString())).thenReturn(new User());
		mockMvc.perform(get("/edit-user-{login}", "User1")).andExpect(status().isOk())
				.andExpect(model().attribute("user", instanceOf(User.class))).andExpect(model().attribute("edit", true))
				.andExpect(model().attribute("action_user", "Update")).andExpect(view().name("registration"));
		verify(userService, times(1)).findByLogin("User1");

	}

	@WithMockUser(roles = "ADMIN")
	@Test
	public void testDeleteUser() throws Exception {
		mockMvc.perform(get("/delete-user-{login}", "User1")).andExpect(status().isFound())
				.andExpect(redirectedUrl("/list"));
		verify(userService, times(1)).deleteByLogin("User1");
	}

	@WithMockUser(roles = "GUEST")
	@Test
	public void testRegistr() throws Exception {
		mockMvc.perform(get("/registr")).andExpect(status().isOk())
				.andExpect(model().attributeExists("roles", "user", "action_user"))
				.andExpect(view().name("registration"));
		verify(userProfileService, times(2)).findAll();
	}

	@WithMockUser(roles = "GUEST")
	@Test
	public void testRegistration() throws Exception {
		User user = createUser();
		MockHttpServletRequest request = new MockHttpServletRequest();
		when(userService.isUserLoginUnique(anyString())).thenReturn(false);
		when(appController.isCaptchaValid(request)).thenReturn(true);
		mockMvc.perform(post("/registr").param("id", String.valueOf(user.getId())).param("login", user.getLogin())
				.param("password", user.getPassword()).param("firstName", user.getFirstName())
				.param("lastName", user.getLastName()).param("email", user.getEmail())
				.param("role", user.getRoleId().getName())).andExpect(status().isOk())
				.andExpect(model().attributeExists("registr")).andExpect(view().name("registrationsuccess"))
				.andExpect(model().hasNoErrors());
		ArgumentCaptor<User> boundUser = ArgumentCaptor.forClass(User.class);
		verify(userService).saveUser(boundUser.capture());
		assertEquals(user.getId(), boundUser.getValue().getId());
		assertEquals(user.getFirstName(), boundUser.getValue().getFirstName());
		assertEquals(user.getLastName(), boundUser.getValue().getLastName());
		assertEquals(user.getPassword(), boundUser.getValue().getPassword());
		assertEquals(user.getEmail(), boundUser.getValue().getEmail());
		verify(userService, times(1)).isUserLoginUnique("User1");
		verify(userService, times(1)).saveUser(boundUser.capture());

	}

	@WithMockUser(roles = "ADMIN")
	@Test
	public void testCreate() throws Exception {
		User user = createUser();
		MockHttpServletRequest request = new MockHttpServletRequest();
		when(userService.isUserLoginUnique(anyString())).thenReturn(false);
		when(appController.isCaptchaValid(request)).thenReturn(true);
		mockMvc.perform(post("/newuser").param("id", String.valueOf(user.getId())).param("login", user.getLogin())
				.param("password", user.getPassword()).param("firstName", user.getFirstName())
				.param("lastName", user.getLastName()).param("email", user.getEmail())
				.param("role", user.getRoleId().getName())).andExpect(status().isOk())
				.andExpect(model().attributeExists("success")).andExpect(view().name("registrationsuccess"))
				.andExpect(model().hasNoErrors());
		ArgumentCaptor<User> boundUser = ArgumentCaptor.forClass(User.class);
		verify(userService).saveUser(boundUser.capture());
		assertEquals(user.getId(), boundUser.getValue().getId());
		assertEquals(user.getFirstName(), boundUser.getValue().getFirstName());
		assertEquals(user.getLastName(), boundUser.getValue().getLastName());
		assertEquals(user.getPassword(), boundUser.getValue().getPassword());
		assertEquals(user.getEmail(), boundUser.getValue().getEmail());
		verify(userService, times(1)).isUserLoginUnique("User1");
		verify(userService, times(1)).saveUser(boundUser.capture());
	}

	@WithMockUser(roles = "ADMIN")
	@Test
	public void testEdit() throws Exception {
		User user = createUser();
		when(userService.isUserLoginUnique(anyString())).thenReturn(false);
		MockHttpServletRequest request = new MockHttpServletRequest();
		when(appController.isCaptchaValid(request)).thenReturn(true);
		mockMvc.perform(post("/edit-user-{login}", user.getLogin()).param("id", String.valueOf(user.getId()))
				.param("login", user.getLogin()).param("password", user.getPassword())
				.param("firstName", user.getFirstName()).param("lastName", user.getLastName())
				.param("email", user.getEmail()).param("role", user.getRoleId().getName())).andExpect(status().isOk())
				.andExpect(model().attributeExists("success")).andExpect(view().name("registrationsuccess"))
				.andExpect(model().hasNoErrors());
		ArgumentCaptor<User> boundUser = ArgumentCaptor.forClass(User.class);
		verify(userService).updateUser(boundUser.capture());
		assertEquals(user.getId(), boundUser.getValue().getId());
		assertEquals(user.getFirstName(), boundUser.getValue().getFirstName());
		assertEquals(user.getLastName(), boundUser.getValue().getLastName());
		assertEquals(user.getPassword(), boundUser.getValue().getPassword());
		assertEquals(user.getEmail(), boundUser.getValue().getEmail());
		verify(userService, times(1)).isUserLoginUnique("User1");
		verify(userService, times(1)).updateUser(boundUser.capture());
	}

	private User createUser() {
		Integer id = 1;
		String login = "User1";
		String password = "User1";
		String fName = "firstName";
		String lName = "lastName";
		String email = "nixsol";
		GregorianCalendar calendar = new GregorianCalendar(2001, 9, 9);
		Date date = new Date(calendar.getTimeInMillis());
		User user = new User();
		user.setId(id);
		user.setLogin(login);
		user.setPassword(password);
		user.setFirstName(fName);
		user.setLastName(lName);
		user.setEmail(email);
		user.setBirthday(date);
		Role role = new Role();
		role.setId(1);
		role.setName("User");
		user.setRoleId(role);
		return user;
	}

}
