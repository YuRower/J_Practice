import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.nixsolutions.webservices.soap.Role;
import com.nixsolutions.webservices.soap.SoapWebService;
import com.nixsolutions.webservices.soap.User;
import com.nixsolutions.webservices.soap.UserSoapFail_Exception;
import com.nixsolutions.webservices.soap.UserSoapService;

public class UserSoapServiceTest {
	protected static UserSoapService userSoapService;
	protected static SoapWebService soapControllerUserService;
	User testUser;

	@BeforeClass
	public static void generalSetUp() throws DatatypeConfigurationException {
		soapControllerUserService = new SoapWebService();
		userSoapService = soapControllerUserService.getUserSoapServiceImplPort();
	}

	@Before
	public void setUp() throws UserSoapFail_Exception {
		testUser = createUser();
		userSoapService.create(testUser);
		User actualUser = userSoapService.findByLogin(testUser.getLogin());

		if (actualUser == null) {
			throw new RuntimeException("User can't be null");
		}
		testUser.setId(actualUser.getId());
	}

	@After
	public void tearDown() throws UserSoapFail_Exception {
		User user = userSoapService.findByLogin(testUser.getLogin());
		if (user != null) {
			userSoapService.remove(testUser.getLogin());
		}

	}

	@Test
	public void getAllUsers() {
		assertNotNull(userSoapService.findAll());
	}

	@Test
	public void createTestUser() throws UserSoapFail_Exception {
		tearDown();
		User actualUser = userSoapService.findByLogin(testUser.getLogin());
		assertNull("User must not exists in db", actualUser);
		userSoapService.create(testUser);
		actualUser = userSoapService.findByLogin(testUser.getLogin());
		assertNotNull("User must exists in db", actualUser);
		testUser.setId(actualUser.getId());
		equalsUsers(testUser, actualUser);
	}

	@Test
	public void testFindByLogin() throws UserSoapFail_Exception {
		User user = userSoapService.findByLogin(testUser.getLogin());
		assertNotNull("User must exists in db", user);
		equalsUsers(testUser, user);
	}

	@Test(expected = UserSoapFail_Exception.class)
	public void testCreateDublicate() throws UserSoapFail_Exception {
		userSoapService.create(testUser);
	}

	@Test
	public void testUpdate() throws UserSoapFail_Exception {
		testUser.setFirstName("fName");
		testUser.setLastName("lName");
		userSoapService.update(testUser);
		User actualUser = userSoapService.findByLogin(testUser.getLogin());
		testUser.setId(actualUser.getId());
		equalsUsers(testUser, actualUser);
	}

	@Test
	public void testRemove() throws UserSoapFail_Exception {
		User actualUser = userSoapService.findByLogin(testUser.getLogin());
		assertNotNull("User must exists in db", actualUser);
		userSoapService.remove(testUser.getLogin());
		User dbUser = userSoapService.findByLogin(testUser.getLogin());
		assertNull("Deleted user still in db", dbUser);
	}

	@Test
	public void testNotExistsUserLogin() throws UserSoapFail_Exception {
		User user = userSoapService.findByLogin("demoLogin");
		assertNull("User must be null", user);
	}

	private User createUser() {
		String login = "User1TEST";
		String password = "User1";
		String fName = "firstName";
		String lName = "lastName";
		String email = "nixsol@mail.com";
		User user = new User();
		user.setLogin(login);
		user.setPassword(password);
		user.setFirstName(fName);
		user.setLastName(lName);
		user.setEmail(email);
		user.setBirthday(getXmlCalendar(2001, 9, 9));
		Role role = new Role();
		role.setId(1);
		role.setName("USER");
		user.setRoleId(role);
		return user;
	}

	private XMLGregorianCalendar getXmlCalendar(int year, int month, int day) {
		GregorianCalendar calendar = new GregorianCalendar(year, month, day);
		try {
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private void equalsUsers(User expected, User actual) {
		assertEquals("Id is not equals", expected.getId(), actual.getId());
		assertEquals("Login is not equals", expected.getLogin(), actual.getLogin());
		assertEquals("Email is not equals", expected.getEmail(), actual.getEmail());
		assertEquals("First name is not equals", expected.getFirstName(), actual.getFirstName());
		assertEquals("Last name is not equals", expected.getLastName(), actual.getLastName());
		assertEquals("Birthday is not equals", expected.getBirthday(), actual.getBirthday());
		assertEquals("Role id is not equals", expected.getRoleId().getId(), actual.getRoleId().getId());
		assertEquals("Role name is not equals", expected.getRoleId().getName(), actual.getRoleId().getName());
	}

}
