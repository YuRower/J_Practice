package com.nixsolutions.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nixsolutions.configuration.AppConfig;
import com.nixsolutions.model.User;
import com.nixsolutions.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
public class CommonControllerTest {

	@Mock
	private UserService userService;
	@Mock
	private PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;
	@Mock
	private AuthenticationTrustResolver authenticationTrustResolver;
	@InjectMocks
	private CommonController commonController;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(commonController).build();
	}

	@WithMockUser(roles = "ADMIN")
	@Test
	public void testAdminAccess() throws Exception {
		List<User> userList = new ArrayList<>();
		userList.add(new User());
		userList.add(new User());
		when(userService.findAllUsers()).thenReturn(userList);
		mockMvc.perform(get("/")).andExpect(status().isOk())
				.andExpect(model().attributeExists("personLogin", "userList")).andExpect(view().name("admin"))
				.andExpect(model().attribute("userList", hasSize(2)));
		verify(userService, times(1)).findAllUsers();

	}

	@WithMockUser(roles = "USER")
	@Test
	public void testUserAccess() throws Exception {
		List<User> userList = new ArrayList<>();
		when(userService.findAllUsers()).thenReturn(userList);
		mockMvc.perform(get("/")).andExpect(status().isOk())
				.andExpect(model().attributeExists("personLogin", "userList")).andExpect(view().name("user"));
	}

	@WithMockUser(roles = "GUEST")
	@Test
	public void testGuestAccess() throws Exception {
		List<User> userList = new ArrayList<>();
		when(userService.findAllUsers()).thenReturn(userList);
		mockMvc.perform(get("/")).andExpect(status().isOk())
				.andExpect(model().attributeExists("personLogin", "userList")).andExpect(view().name("login"));
	}

	@WithMockUser(roles = "USER")
	@Test
	public void testLogout() throws Exception {
		mockMvc.perform(get("/logout")).andExpect(redirectedUrl("/login?logout"));
	}

}