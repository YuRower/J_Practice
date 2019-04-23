package com.nixsolutions.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nixsolutions.exception.DBException;
import com.nixsolutions.exception.Messages;
import com.nixsolutions.model.User;

@Repository("userDao")
public class HibernateUserDao extends AbstractDao<Integer, User> implements UserDao {

	static final Logger LOGGER = LoggerFactory.getLogger(HibernateUserDao.class);

	@Override
	public void create(User user) {
		try {
			LOGGER.trace("Create user {} ", user);
			persist(user);
		} catch (Exception ex) {
			LOGGER.error(Messages.ERR_CANNOT_CREATE_USER, ex);
			throw new DBException(Messages.ERR_CANNOT_CREATE_USER, ex);
		}
	}

	@Override
	public void update(User user) {
		try {
			LOGGER.trace("Update user {} ", user);
			update(user);
		} catch (Exception ex) {
			LOGGER.error(Messages.ERR_CANNOT_UPDATE_USER, ex);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_USER, ex);
		}
	}

	@Override
	public void remove(User user) {
		try {
			LOGGER.trace("Remove user {} ", user);
			delete(user);
		} catch (Exception ex) {
			LOGGER.error(Messages.ERR_CANNOT_REMOVE_USER, ex);
			throw new DBException(Messages.ERR_CANNOT_REMOVE_USER, ex);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public User findByLogin(String login) {
		User user;
		try {
			Criteria criteria = createEntityCriteria();
			criteria.add(Restrictions.eq("login", login));
			user = (User) criteria.uniqueResult();
			LOGGER.info("found user by login ---> {} ", user);

		} catch (Exception ex) {
			LOGGER.error(Messages.ERR_CANNOT_FIND_USER_BY_LOGIN);
			throw new DBException(Messages.ERR_CANNOT_FIND_USER_BY_LOGIN, ex);
		}
		return user;
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findAll() {
		List<User> users = new ArrayList<>();
		try {
			Criteria criteria = createEntityCriteria().addOrder(Order.desc("firstName"));
			users = criteria.list();
		} catch (Exception ex) {
			LOGGER.error(Messages.ERR_CANNOT_FIND_USERS);
			throw new DBException(Messages.ERR_CANNOT_FIND_USERS, ex);

		}
		return users;
	}

	@Override
	@Transactional(readOnly = true)
	public User findByEmail(String email) {
		User user;
		try {
			Criteria criteria = createEntityCriteria();
			criteria.add(Restrictions.eq("email", email));
			user = (User) criteria.uniqueResult();
		} catch (Exception ex) {
			LOGGER.error(Messages.ERR_CANNOT_FIND_USER_BY_EMAIL);
			throw new DBException(Messages.ERR_CANNOT_FIND_USER_BY_EMAIL, ex);
		}
		return user;
	}

}
