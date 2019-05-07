package com.nixsolutions.web_services;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nixsolutions.model.Role;
import com.nixsolutions.model.User;
import com.nixsolutions.model.UserListWrapper;

public class UserResourceTest {
	private static final String BASE_URL = "http://10.10.35.192:8080/Web_Services/rest/users";
	private static final String URL_RESOURCE = "http://10.10.35.192:8080/Web_Services/rest/users/user/{login}";
	private static final String NON_EXISTS_LOGIN = "nonExistsLogin";

	private RestTemplate restTemplate;
	private User testUser;

	@Before
	public void setUp() {
		restTemplate = new RestTemplate();
		restTemplate.setMessageConverters(getMessageConverters());
		testUser = createUser();
		saveUser(testUser);
		// testUser.setId(getIdByLogin(testUser.getLogin()));
	}

	@After
	public void tearDown() {
		restTemplate.delete(URL_RESOURCE, testUser.getLogin());

	}

	@Test
	public void testCreate() {
		tearDown();
		final HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType((MediaType.APPLICATION_JSON));
		final HttpEntity<User> entity = new HttpEntity<User>(testUser, headers);

		final ResponseEntity<User> response = restTemplate.exchange(BASE_URL, HttpMethod.POST, entity, User.class,
				testUser.getLogin());
		User actualUser = restTemplate.getForObject(URL_RESOURCE, User.class, testUser.getLogin());
		testUser.setId(actualUser.getId());
		assertEquals("Http status code must be " + HttpStatus.CREATED, HttpStatus.CREATED, response.getStatusCode());
		equalsUsers(testUser, actualUser);
	}

	private void saveUser(User resource) {
		resource = createUser();
		final HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType((MediaType.APPLICATION_JSON));
		final HttpEntity<User> entity = new HttpEntity<User>(resource, headers);

		final ResponseEntity<User> response = restTemplate.exchange(BASE_URL, HttpMethod.POST, entity, User.class,
				resource.getLogin());

	}

	@Test(expected = HttpClientErrorException.class)
	public void testCreateWithLoginDublicate() {
		User testUser = createUser();
		final HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType((MediaType.APPLICATION_JSON));
		final HttpEntity<User> entity = new HttpEntity<User>(testUser, headers);
		final ResponseEntity<User> response = restTemplate.exchange(BASE_URL, HttpMethod.POST, entity, User.class,
				testUser.getLogin());
		// assertEquals("Http status code must be " + HttpStatus.CONFLICT,
		// HttpStatus.CONFLICT, response.getStatusCode());
	}

	@Test
	public void testPutUser() {
		testUser.setFirstName("fName");
		testUser.setEmail("TestMAIL");
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType((MediaType.APPLICATION_JSON));
		HttpEntity<User> entity = new HttpEntity<User>(testUser, headers);
		ResponseEntity<User> response = restTemplate.exchange(URL_RESOURCE, HttpMethod.PUT, entity, User.class,
				testUser.getLogin());
		User actualUser = restTemplate.getForObject(URL_RESOURCE, User.class, testUser.getLogin());
		testUser.setId(actualUser.getId());
		assertEquals("Http status code must be " + HttpStatus.NO_CONTENT, HttpStatus.NO_CONTENT,
				response.getStatusCode());
		equalsUsers(testUser, actualUser);
	}

	@Test
	public void testDelete() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType((MediaType.APPLICATION_JSON));
		ResponseEntity<Void> response = restTemplate.exchange(URL_RESOURCE, HttpMethod.DELETE, null, Void.class,
				testUser.getLogin());
		assertEquals("Http status code must be " + HttpStatus.ACCEPTED, HttpStatus.ACCEPTED, response.getStatusCode());
		try {
			getIdByLogin(testUser.getLogin());
			fail("User must not exists");
		} catch (RuntimeException ex) {
		}
		setUp();

	}

	@Test(expected = HttpClientErrorException.class)
	public void testUpdateNonExistsId() {
		User testUser = createUser();
		final HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType((MediaType.APPLICATION_JSON));
		final HttpEntity<User> entity = new HttpEntity<User>(testUser, headers);
		final ResponseEntity<User> response = restTemplate.exchange(BASE_URL, HttpMethod.PUT, entity, User.class,
				NON_EXISTS_LOGIN);
	}

	@Test(expected = HttpClientErrorException.class)
	public void testDeletWithNonExistsId() {
		restTemplate.delete(URL_RESOURCE, NON_EXISTS_LOGIN);
	}

	@Test
	public void findUserByIdTest() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		// headers.setContentType((MediaType.APPLICATION_JSON));

		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity<User> response = restTemplate.exchange(URL_RESOURCE, HttpMethod.GET, entity, User.class,
				testUser.getLogin());
		assertEquals("Http status code must be " + HttpStatus.OK, HttpStatus.OK, response.getStatusCode());
		User actualUser = restTemplate.getForObject(URL_RESOURCE, User.class, testUser.getLogin());
		testUser.setId(actualUser.getId());
		assertThat(actualUser, notNullValue());
		equalsUsers(testUser, actualUser);

	}

	@Test
	public void testFindAll() throws JsonParseException, JsonMappingException, IOException, JSONException {
		JSONObject json = readJsonFromUrl(BASE_URL);
		UserListWrapper readValues = new ObjectMapper().readValue(json.toString(), UserListWrapper.class);
		assertNotNull("User list must not be empty", readValues.getItems());
	}

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}
	}

	private List<HttpMessageConverter<?>> getMessageConverters() {
		List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
		converters.add(converter);
		return converters;
	}

	private User createUser() {
		String login = "User1Test1";
		String password = "User1";
		String fName = "firstName";
		String lName = "lastName";
		String email = "nixsolution";

		GregorianCalendar calendar = new GregorianCalendar(2001, 9, 9);
		Date date = new Date(calendar.getTimeInMillis());
		User user = new User();
		user.setLogin(login);
		user.setPassword(password);
		user.setFirstName(fName);
		user.setLastName(lName);
		user.setEmail(email);
		user.setBirthday(date);
		Role role = new Role();
		role.setId(1);
		role.setName("USER");
		user.setRoleId(role);
		return user;

	}

	private Integer getIdByLogin(String login) {
		User[] userList = restTemplate.getForObject(BASE_URL, User[].class);
		for (User user : userList) {
			if (user.getLogin().equals(login)) {
				return user.getId();
			}
		}
		throw new RuntimeException("User with login " + login + " not found in db");
	}

	private void equalsUsers(User expected, User actual) {
		assertEquals("Id is not equals", expected.getId(), actual.getId());
		assertEquals("Login is not equals", expected.getLogin(), actual.getLogin());
		assertEquals("Email is not equals", expected.getEmail(), actual.getEmail());
		assertEquals("First name is not equals", expected.getFirstName(), actual.getFirstName());
		assertEquals("Last name is not equals", expected.getLastName(), actual.getLastName());
		// assertEquals("Birthday is not equals", expected.getBirthday(),
		// actual.getBirthday());
		assertEquals("Role id is not equals", expected.getRoleId().getId(), actual.getRoleId().getId());
		assertEquals("Role name is not equals", expected.getRoleId().getName(), actual.getRoleId().getName());
	}

}
