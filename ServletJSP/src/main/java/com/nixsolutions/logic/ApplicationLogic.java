package com.nixsolutions.logic;

import java.util.List;

import com.nixsolutions.dao.RoleDao;
import com.nixsolutions.dao.UserDao;
import com.nixsolutions.dao.h2.JdbcRoleDao;
import com.nixsolutions.dao.h2.JdbcUserDao;
import com.nixsolutions.entity.User;

public abstract class ApplicationLogic {

	RoleDao roleDao = new JdbcRoleDao();
	UserDao userDao = new JdbcUserDao();

	public abstract User findUserByLogin(String login);

	public abstract User findByEmail(String email);

	public abstract List<User> findAll();

	public abstract void newUserWithDefaultValue(User user);

	public abstract void remove(String login);

	public abstract void update(User user);

}
