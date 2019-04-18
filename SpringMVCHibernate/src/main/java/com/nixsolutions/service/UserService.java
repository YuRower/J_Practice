package com.nixsolutions.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.nixsolutions.model.User;

@Transactional
public interface UserService {

	User findByLogin(String login);

	void saveUser(User user);

	void updateUser(User user);

	void deleteByLogin(String login);

	List<User> findAllUsers();

	boolean isUserLoginUnique(String login);

}