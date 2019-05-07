package com.nixsolutions.webservices.soap;

import java.util.List;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nixsolutions.exception.UserSoapFail;
import com.nixsolutions.model.User;
import com.nixsolutions.service.UserService;

@WebService(endpointInterface = "com.nixsolutions.webservices.soap.UserSoapService", serviceName = "SoapWebService")
@Component
public class UserSoapServiceImpl implements UserSoapService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserSoapService.class);
	@Autowired
	private UserService userService;

	@Override
	public User findByLogin(String login) throws UserSoapFail {
		return userService.findByLogin(login);
	}

	@Override
	public List<User> findAll() {
		LOGGER.debug(" findAll {}");

		return userService.findAllUsers();
	}

	@Override
	public String create(User user) throws UserSoapFail {
		if (userService.isUserLoginUnique(user.getLogin())) {
			throw new UserSoapFail("User with such login alredy registered");
		}
		LOGGER.debug(" create {}", user);
		userService.saveUser(user);
		return "user succesufuly created";

	}

	@Override
	public String update(User user) throws UserSoapFail {
		LOGGER.debug(" update {}", user);
		User userExist = userService.findByLogin(user.getLogin());
		if (userExist == null) {
			throw new UserSoapFail("User user does not exist ");
		}
		userService.updateUser(user);
		return "user succesufuly updated";
	}

	@Override
	public String remove(String login) throws UserSoapFail {
		LOGGER.debug(" remove login {}", login);
		User user = userService.findByLogin(login);
		if (user == null) {
			throw new UserSoapFail("User user does not exist ");
		}
		userService.delete(user);
		return "user succesufuly deleted";
	}
}
