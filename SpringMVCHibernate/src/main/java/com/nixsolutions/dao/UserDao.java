package com.nixsolutions.dao;

import java.util.List;

import com.nixsolutions.model.User;

public interface UserDao {

	User findByLogin(String login);

	List<User> findAll();

	void create(User user);

	void update(User user);

	void remove(User user);

	User findByEmail(String email);

}
