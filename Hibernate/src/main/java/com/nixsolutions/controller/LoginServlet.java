package com.nixsolutions.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.nixsolutions.entity.User;
import com.nixsolutions.entity.UserType;
import com.nixsolutions.exception.ApplicationException;
import com.nixsolutions.logic.ApplicationLogic;
import com.nixsolutions.logic.UserLogic;
import com.nixsolutions.util.Path;

public class LoginServlet extends HttpServlet {

	static Logger LOGGER = Logger.getLogger(LoginServlet.class);

	private static final long serialVersionUID = 1L;

	private ApplicationLogic userLogic = new UserLogic();

	public LoginServlet() {
		super();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		LOGGER.trace("Request received");

		HttpSession session = request.getSession();
		String login = request.getParameter("login");
		String password = request.getParameter("pwd");
		User user = validateUser(login, password);

		UserType userType = UserType.getType(user);

		LOGGER.trace("User type " + userType);

		String path = defineForwardAddressByUserRole(userType);

		session.setAttribute("user", user);

		LOGGER.trace("Set the session attribute: user --> " + user);

		session.setAttribute("userType", userType);

		LOGGER.trace("Set the session attribute: userType --> " + userType);

		LOGGER.info("User " + user + " logged as " + userType.toString().toLowerCase());

		LOGGER.trace("Forward address --> " + path);
		LOGGER.debug("Controller finished, now go to forward address --> " + path);
		response.sendRedirect(request.getContextPath() + path);
	}

	private String defineForwardAddressByUserRole(UserType userType) {
		switch (userType) {
		case ADMIN:
			return Path.ADMIN_PAGE;
		case USER:
			return Path.USER_PAGE;
		default:
			throw new ApplicationException("Unresolved usertype");
		}
	}

	private User validateUser(String login, String password) {
		LOGGER.trace("Request parameter: login --> " + login);

		LOGGER.trace("Request parameter: password --> " + password);

		User user = null;
		if (login == null || password == null || login.trim().isEmpty() || password.trim().isEmpty()) {
			throw new ApplicationException("login/password cannot be empty");
		}

		user = userLogic.findUserByLogin(login);
		LOGGER.trace("Found in DB: user --> " + user);

		if (user == null || !checkPassword(password, user.getPassword())) {
			throw new ApplicationException("Cannot find user with such login/password");
		}
		return user;
	}

	private boolean checkPassword(String password, String userPassword) {
		return password.equals(userPassword) ? true : false;
	}

}
