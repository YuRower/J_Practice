package com.nixsolutions.dao.h2.orm;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nixsolutions.dao.UserDao;
import com.nixsolutions.entity.User;
import com.nixsolutions.exception.DBException;
import com.nixsolutions.exception.Messages;

public class HibernateUserDao extends H2OrmDao implements UserDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(HibernateUserDao.class);

	private SessionFactory sessionFactory;

	public HibernateUserDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void create(User user) {
		LOGGER.trace("Create user {} ", user);
		Session session = null;
		Transaction tx;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.save(user);
			tx.commit();
		} catch (Exception ex) {
			LOGGER.error(Messages.ERR_CANNOT_CREATE_USER, ex);
			rollbackTrancaction(session);
			throw new DBException(Messages.ERR_CANNOT_CREATE_USER, ex);
		} finally {
			closeSession(session);
		}
	}

	public void update(User user) {
		LOGGER.trace("Update user {} ", user);

		Session session = null;
		Transaction tx;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.update(user);
			tx.commit();
		} catch (Exception ex) {
			LOGGER.error(Messages.ERR_CANNOT_UPDATE_USER, ex);
			rollbackTrancaction(session);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_USER, ex);
		} finally {
			closeSession(session);

		}
	}

	public void remove(User user) {
		LOGGER.trace("Remove user {} ", user);
		Session session = null;
		Transaction tx;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.remove(user);
			tx.commit();
		} catch (Exception ex) {
			LOGGER.error(Messages.ERR_CANNOT_REMOVE_USER, ex);
			rollbackTrancaction(session);
			throw new DBException(Messages.ERR_CANNOT_REMOVE_USER, ex);
		} finally {
			closeSession(session);

		}
	}

	public List<User> findAll() {
		Session session = null;
		Transaction tx;
		List<User> users = new ArrayList<User>();

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			users = session.createQuery("from User", User.class).list();
			LOGGER.trace("Find all users {} ", users);
			tx.commit();
		} catch (Exception ex) {
			LOGGER.error(Messages.ERR_CANNOT_FIND_USERS, ex);
			rollbackTrancaction(session);
			throw new DBException(Messages.ERR_CANNOT_FIND_USERS, ex);
		} finally {
			closeSession(session);
		}
		return users;
	}

	public User findByLogin(String login) {
		LOGGER.trace("Find  user by login -- > {} ", login);

		Session session = null;
		Transaction tx;
		User user = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<User> criteria = builder.createQuery(User.class);
			Root<User> userRoot = criteria.from(User.class);
			criteria.select(userRoot);
			criteria.where(builder.equal(userRoot.get("login"), login));
			user = session.createQuery(criteria).uniqueResult();
			LOGGER.trace("Find  user {} ", user);

			tx.commit();
		} catch (Exception ex) {
			LOGGER.error(Messages.ERR_CANNOT_FIND_USER_BY_LOGIN, ex);
			rollbackTrancaction(session);
			throw new DBException(Messages.ERR_CANNOT_FIND_USER_BY_LOGIN, ex);
		} finally {
			closeSession(session);
		}
		return user;
	}

	public User findByEmail(String email) {
		LOGGER.trace("Find  user by email -- > {} ", email);

		Session session = null;
		Transaction tx;
		User user = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<User> criteria = builder.createQuery(User.class);
			Root<User> userRoot = criteria.from(User.class);
			criteria.select(userRoot);
			criteria.where(builder.equal(userRoot.get("email"), email));
			user = session.createQuery(criteria).uniqueResult();
			LOGGER.trace("Find  user {} ", user);

			tx.commit();
		} catch (Exception ex) {
			LOGGER.error(Messages.ERR_CANNOT_FIND_USER_BY_EMAIL, ex);
			rollbackTrancaction(session);
			throw new DBException(Messages.ERR_CANNOT_FIND_USER_BY_EMAIL, ex);
		} finally {
			closeSession(session);
		}
		return user;
	}
}
