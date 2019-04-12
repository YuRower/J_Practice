package com.nixsolutions.logic;

import java.util.List;

import org.apache.log4j.Logger;

import com.nixsolutions.entity.User;
import com.nixsolutions.exception.ApplicationException;

public class UserLogic extends ApplicationLogic {
	private static final Logger LOGGER = Logger.getLogger(UserLogic.class);

	@Override
	public User findUserByLogin(String login) {
		LOGGER.trace("Find user by login" + login);
		User user = userDao.findByLogin(login);
		return user;
	}

	@Override
	public List<User> findAll() {
		LOGGER.trace("Find all users");
		return userDao.findAll();
	}

	@Override
	public User findByEmail(String email) {
		LOGGER.trace("Find user by email: " + email);
		return userDao.findByEmail(email);
	}

	private void checkLogin(String login) {
		if (userDao.findByLogin(login) != null) {
			String msg = "User with such login '" + login + "' is already exists";
			LOGGER.debug(msg);
			throw new ApplicationException(msg);
		}
	}

	private void checkEmail(String email) {
		if (userDao.findByEmail(email) != null) {
			String msg = "User with such email '" + email + "' is already exists";
			LOGGER.debug(msg);
			throw new ApplicationException(msg);
		}
	}

	@Override
	public void remove(String login) {
		LOGGER.trace("Remove user by login");
		User user = userDao.findByLogin(login);
		if (user != null) {
			userDao.remove(user);
		} else {
			throw new ApplicationException("User doesnt exist");

		}
	}

	@Override
	public void newUserWithDefaultValue(User user) {
		LOGGER.trace("Create user");
		checkLogin(user.getLogin());
		checkEmail(user.getEmail());
		userDao.create(user);
	}

	@Override
	public void update(User newUser) {
		LOGGER.trace("Update user");
		User oldUser = findUserByLogin(newUser.getLogin());
		LOGGER.debug("Found with such login" + newUser);
		newUser.setId(oldUser.getId());
		userDao.update(newUser);
	}

}
