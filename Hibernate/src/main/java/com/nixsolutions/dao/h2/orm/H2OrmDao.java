package com.nixsolutions.dao.h2.orm;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.nixsolutions.connection.HibernateUtil;
import com.nixsolutions.dao.AbstractDAOFactory;
import com.nixsolutions.dao.RoleDao;
import com.nixsolutions.dao.UserDao;
import com.nixsolutions.exception.Messages;

public class H2OrmDao extends AbstractDAOFactory {

	private static final Logger LOGGER = Logger.getLogger(H2OrmDao.class);
	private static H2OrmDao instance;

	public synchronized static H2OrmDao getInstance() {
		if (instance == null) {
			instance = new H2OrmDao();
		}
		return instance;

	}

	public void rollbackTrancaction(Session session) {
		if (session != null && session.getTransaction() != null)
			try {
				session.getTransaction().rollback();
			} catch (Exception ex) {
				LOGGER.error(Messages.ERR_CANNOT_ROLLBACK_TRANSACTION, ex);
			}
	}

	public void closeSession(Session session) {
		if (session != null && !session.isOpen()) {
			try {
				session.close();
			} catch (Exception ex) {
				LOGGER.error(Messages.ERR_CANNOT_CLOSE_SESSION, ex);
			}
		}
	}

	@Override
	public RoleDao getRoleDAO() {
		return new HibernateRoleDao(HibernateUtil.getSessionFactory());
	}

	@Override
	public UserDao getUserDAO() {
		return new HibernateUserDao(HibernateUtil.getSessionFactory());
	}

}
