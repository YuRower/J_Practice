package com.nixsolutions.dao.h2.orm;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nixsolutions.dao.RoleDao;
import com.nixsolutions.entity.Role;
import com.nixsolutions.exception.DBException;
import com.nixsolutions.exception.Messages;

public class HibernateRoleDao extends H2OrmDao implements RoleDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(HibernateUserDao.class);

	private static final String HQL_FIND_ROLE_BY_NAME = "SELECT r.id, r.name from ROLE r WHERE name = :name";

	private SessionFactory sessionFactory;

	public HibernateRoleDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void create(Role role) {
		LOGGER.trace("Create role {} ", role);
		Session session = null;
		Transaction tx;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.save(role);
			tx.commit();

		} catch (Exception ex) {
			LOGGER.error(Messages.ERR_CANNOT_CREATE_ROLE, ex);
			rollbackTrancaction(session);
			throw new DBException(Messages.ERR_CANNOT_CREATE_ROLE, ex);
		} finally {
			closeSession(session);

		}
	}

	public void update(Role role) {
		LOGGER.trace("Update role {} ", role);

		Session session = null;
		Transaction tx;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.update(role);
			tx.commit();
		} catch (Exception ex) {
			LOGGER.error(Messages.ERR_CANNOT_UPDATE_ROLE, ex);
			rollbackTrancaction(session);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_ROLE, ex);
		} finally {
			closeSession(session);

		}
	}

	public void remove(Role role) {
		LOGGER.trace("Remove role {} ", role);

		Session session = null;
		Transaction tx;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.remove(role);
			tx.commit();
		} catch (Exception ex) {
			LOGGER.error(Messages.ERR_CANNOT_REMOVE_ROLE, ex);
			rollbackTrancaction(session);
			throw new DBException(Messages.ERR_CANNOT_REMOVE_ROLE, ex);
		} finally {
			closeSession(session);

		}
	}

	public Role findByName(String name) {
		LOGGER.trace("Find  role by name -- > {} ", name);

		Session session = null;
		Transaction tx;
		Role role;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Query<Role> query = session.createQuery(HQL_FIND_ROLE_BY_NAME, Role.class);
			query.setParameter("name", name);
			role = query.uniqueResult();
			tx.commit();
		} catch (Exception ex) {
			LOGGER.error(Messages.ERR_CANNOT_FIND_ROLE, ex);
			rollbackTrancaction(session);
			throw new DBException(Messages.ERR_CANNOT_FIND_ROLE, ex);
		} finally {
			closeSession(session);

		}
		return role;

	}
}
