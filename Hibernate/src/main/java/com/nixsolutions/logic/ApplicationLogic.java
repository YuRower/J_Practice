package com.nixsolutions.logic;

import java.util.List;

import com.nixsolutions.dao.AbstractDAOFactory;
import com.nixsolutions.dao.AbstractDAOFactory.FactoryTypes;
import com.nixsolutions.dao.RoleDao;
import com.nixsolutions.dao.UserDao;
import com.nixsolutions.entity.User;

public abstract class ApplicationLogic {

	protected static AbstractDAOFactory ormFactory = AbstractDAOFactory.getDAOFactory(FactoryTypes.ORM);

	RoleDao roleDao = ormFactory.getRoleDAO();
	UserDao userDao = ormFactory.getUserDAO();

	public abstract User findUserByLogin(String login);

	public abstract User findByEmail(String email);

	public abstract List<User> findAll();

	public abstract void newUserWithDefaultValue(User user);

	public abstract void remove(String login);

	public abstract void update(User user);

}
