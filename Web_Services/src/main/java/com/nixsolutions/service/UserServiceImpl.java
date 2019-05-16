package com.nixsolutions.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nixsolutions.dao.UserDao;
import com.nixsolutions.model.User;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	private UserDao dao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/*
	 * @Override public User findById(int id) { return dao.findById(id); }
	 */

	@Override
	public User findByLogin(String login) {
		User user = dao.findByLogin(login);
		return user;
	}

	@Override
	public void saveUser(User user) {
		LOGGER.debug("save user");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		dao.create(user);
	}

	@Override
	public void updateUser(User user) {
		User entity = dao.findByLogin(user.getLogin());
		if (entity != null) {
			// entity.setLogin(user.getLogin());
			if (user.getPassword() != null) {
				if (!user.getPassword().equals(entity.getPassword())) {
					entity.setPassword(passwordEncoder.encode(user.getPassword()));
				}
			}
			entity.setFirstName(user.getFirstName());
			entity.setLastName(user.getLastName());
			entity.setEmail(user.getEmail());
			entity.setBirthday(user.getBirthday());
			entity.setRoleId(user.getRoleId());
			dao.update(entity);
		}

	}

	@Override
	public void update(User user) {
		dao.update(user);
	}

	@Override
	public void delete(User user) {
		dao.remove(user);
	}

	@Override
	public void deleteByLogin(String login) {
		User user = findByLogin(login);
		dao.remove(user);
	}

	@Override
	public List<User> findAllUsers() {
		return dao.findAll();
	}

	@Override
	public boolean isUserLoginUnique(String login) {
		User user = findByLogin(login);

		boolean isUserExists = user != null ? true : false;
		LOGGER.debug("user already exists -- > {}", isUserExists);
		return isUserExists;
	}

}
