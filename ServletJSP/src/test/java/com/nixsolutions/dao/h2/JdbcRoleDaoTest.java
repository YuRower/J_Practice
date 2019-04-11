package com.nixsolutions.dao.h2;

import static org.dbunit.Assertion.assertEqualsIgnoreCols;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.dbunit.dataset.ITable;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.nixsolutions.DBUnitConfig;
import com.nixsolutions.dao.RoleDao;
import com.nixsolutions.entity.Role;

public class JdbcRoleDaoTest extends DBUnitConfig {

	private static final String FULL_DATASET = "dataset/RoleSet/full.xml";
	private static final String EMPTY_TABLE = "dataset/RoleSet/empty.xml";
	private static final String TABLE_NAME = "role";
	private static final String[] IGNORE_COLS = { "role_id" };
	private RoleDao roleDao;
	private Role[] roles;

	@BeforeClass
	public static void generalSetUp() throws Exception {
		new DBUnitConfig().createSchema();
		new DBUnitConfig().createTables();
	}

	@Before
	public void setUp() throws Exception {
		roleDao = new JdbcRoleDao();
		fillTableData(FULL_DATASET);
		roles = new Role[3];

		Role role1 = new Role(1l, "User");
		Role role2 = new Role(2l, "Admin");
		Role role3 = new Role(3l, "SuperUser");

		roles[0] = role1;
		roles[1] = role2;
		roles[2] = role3;
	}

	@Test
	public void testCreate() throws Exception {
		fillTableData(EMPTY_TABLE);
		String afterCreate = "dataset/RoleSet/RoleAfterCreate.xml";
		roleDao.create(roles[0]);
		roleDao.create(roles[1]);
		roleDao.create(roles[2]);

		ITable expected = getTableFromFile(TABLE_NAME, afterCreate);
		ITable actual = getTableFromSchema(TABLE_NAME);

		assertEqualsIgnoreCols(expected, actual, IGNORE_COLS);
	}

	@Test
	public void testUpdate() throws Exception {
		String afterUpdate = "dataset/RoleSet/RoleAfterUpdate.xml";
		Role role = roles[1];
		role.setName("Admin1");

		roleDao.update(role);

		ITable expected = getTableFromFile(TABLE_NAME, afterUpdate);
		ITable actual = getTableFromSchema(TABLE_NAME);

		assertEqualsIgnoreCols(expected, actual, IGNORE_COLS);

	}

	@Test
	public void testRemove() throws Exception {
		String afterRemove = "dataset/RoleSet/RoleAfterRemove.xml";

		roleDao.remove(roles[0]);

		ITable expected = getTableFromFile(TABLE_NAME, afterRemove);
		ITable actual = getTableFromSchema(TABLE_NAME);

		assertEqualsIgnoreCols(expected, actual, IGNORE_COLS);
	}

	@Test
	public void testFindByName() {
		Role role = roleDao.findByName(roles[2].getName());
		assertThat(role.getName(), is("SuperUser"));
	}

}
