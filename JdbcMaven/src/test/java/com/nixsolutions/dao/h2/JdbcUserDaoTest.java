package com.nixsolutions.dao.h2;

import static org.dbunit.Assertion.assertEqualsIgnoreCols;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.ITable;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.nixsolutions.DBUnitConfig;
import com.nixsolutions.dao.UserDao;
import com.nixsolutions.entity.Role;
import com.nixsolutions.entity.User;

public class JdbcUserDaoTest extends DBUnitConfig {
    private static final String DATASET_COMMON = "dataset/UserSet/full.xml";
    private static final String TABLE_EMPTY = "dataset/UserSet/empty.xml";
    private static final String TABLE_NAME = "USER";
    private static final String[] IGNORE_COLS = { "USER_ID" };
    private UserDao userDao;
    private DBUnitConfig dbTestHelper;
    private User[] users;
    private Role[] roles;
    private User userforUpdate = null;

    @BeforeClass
    public static void generalSetUp() throws Exception {

        new DBUnitConfig().createSchema();
        new DBUnitConfig().createTables();

    }

    @Before
    public void setUp() throws Exception {
        userDao = new JdbcUserDao();
        dbTestHelper = new DBUnitConfig();
        dbTestHelper.fillTableData(DATASET_COMMON);

        roles = new Role[3];

        Role role1 = new Role(1l, "User");
        Role role2 = new Role(2l, "Admin");
        Role role3 = new Role(3l, "SuperUser");

        roles[0] = role1;
        roles[1] = role2;
        roles[2] = role3;

        users = new User[3];
        GregorianCalendar calendar = new GregorianCalendar(1999, 10, 10);
        GregorianCalendar calendar2 = new GregorianCalendar(2000, 11, 11);
        GregorianCalendar calendar3 = new GregorianCalendar(2001, 9, 9);

        Date date = new Date(calendar.getTimeInMillis());
        Date date1 = new Date(calendar2.getTimeInMillis());

        Date date2 = new Date(calendar3.getTimeInMillis());

        User user1 = new User(1l, "login", "password", "email", "fN", "lN",
                date, role1.getId());

        User user2 = new User(2l, "login2", "password2", "email2", "fN2", "lN2",
                date1, role2.getId());

        User user3 = new User(3l, "login3", "password3", "email3", "fN3", "lN3",
                date2, role3.getId());
        userforUpdate = new User(3l, "login4", "password4", "email4", "fN4",
                "lN4", date2, role1.getId());
        users[0] = user1;
        users[1] = user2;
        users[2] = user3;
    }

    @Test
    public void testCreate() throws DatabaseUnitException {
        dbTestHelper.fillTableData(TABLE_EMPTY);

        String afterCreate = "dataset/UserSet/UserAfterCreate.xml";

        userDao.create(users[0]);
        userDao.create(users[1]);
        userDao.create(users[2]);

        ITable expected = dbTestHelper.getTableFromFile(TABLE_NAME,
                afterCreate);
        ITable actual = dbTestHelper.getTableFromSchema(TABLE_NAME);

        assertEqualsIgnoreCols(expected, actual, IGNORE_COLS);
    }

    @Test
    public void testUpdate() throws DatabaseUnitException {
        String afterUpdate = "dataset/UserSet/UserAfterUpdate.xml";

        userDao.update(userforUpdate);

        ITable expected = dbTestHelper.getTableFromFile(TABLE_NAME,
                afterUpdate);
        ITable actual = dbTestHelper.getTableFromSchema(TABLE_NAME);

        assertEqualsIgnoreCols(expected, actual, IGNORE_COLS);

    }

    @Test
    public void testRemove() throws DatabaseUnitException {
        String afterRemove = "dataset/UserSet/UserAfterRemove.xml";

        userDao.remove(userforUpdate);

        ITable expected = dbTestHelper.getTableFromFile(TABLE_NAME,
                afterRemove);
        ITable actual = dbTestHelper.getTableFromSchema(TABLE_NAME);

        assertEqualsIgnoreCols(expected, actual, IGNORE_COLS);
    }

    @Test
    public void testFindAll() {
        fillTableData(DATASET_COMMON);
        List<User> expected = new ArrayList<>();
        expected.add(users[0]);
        expected.add(users[1]);
        expected.add(users[2]);

        List<User> actual = userDao.findAll();

        assertEquals("User lists must equals", expected, actual);
    }

    @Test
    public void testFindByLogin() {
        User user = userDao.findByLogin(users[1].getLogin());
        assertEquals("Users must equals", users[1], user);
    }

    @Test
    public void testFindByEmail() {
        User user = userDao.findByEmail(users[0].getEmail());
        assertEquals("Users must equals", users[0], user);
    }

}
