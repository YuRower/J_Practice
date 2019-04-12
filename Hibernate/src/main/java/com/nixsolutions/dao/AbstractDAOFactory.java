package com.nixsolutions.dao;

import com.nixsolutions.dao.h2.jdbc.H2JdbcDao;
import com.nixsolutions.dao.h2.orm.H2OrmDao;

public abstract class AbstractDAOFactory {

	public enum FactoryTypes {
		JDBC, ORM
	}

	public abstract UserDao getUserDAO();

	public abstract RoleDao getRoleDAO();

	public static AbstractDAOFactory getDAOFactory(FactoryTypes type) {
		switch (type) {
		case JDBC:
			return H2JdbcDao.getInstance();
		case ORM:
			return H2OrmDao.getInstance();
		default:
			return null;
		}
	}

}
