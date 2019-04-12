package com.nixsolutions.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.nixsolutions.entity.User;
import com.nixsolutions.exception.ApplicationException;

public final class UserFormProccess {

	private static Logger LOGGER = Logger.getLogger(UserFormProccess.class);

	public static User proccessForm(HttpServletRequest request) {

		String firstName = request.getParameter("FirstName");
		LOGGER.info("Request parameter: first name --> " + firstName);

		String lastName = request.getParameter("LastName");
		LOGGER.info("Request parameter: password --> " + lastName);

		String login = request.getParameter("login");
		LOGGER.info("Request parameter: login --> " + login);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date birthday = null;
		try {
			birthday = formatter.parse(request.getParameter("Birthday"));
		} catch (ParseException e) {
			throw new ApplicationException("invalid birthday format");
		}
		LOGGER.info("Request parameter: birthday --> " + birthday);

		String email = request.getParameter("Email");
		LOGGER.info("Request parameter: email --> " + email);

		Integer roleId = getRoleId(request);
		LOGGER.info("Request parameter: role --> " + roleId);

		String password = request.getParameter("password");
		LOGGER.info("Request parameter: password --> " + password);

		User user = new User(-1l, login, password, email, firstName, lastName, birthday, roleId);
		LOGGER.info("User--> " + user);

		return user;
	}

	private static int getRoleId(HttpServletRequest request) {
		String userId = request.getParameter("Role");
		LOGGER.info("Role id --> " + userId);
		return Integer.parseInt(userId);
	}

}
