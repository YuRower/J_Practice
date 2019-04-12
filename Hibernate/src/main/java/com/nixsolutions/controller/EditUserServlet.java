package com.nixsolutions.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.nixsolutions.entity.User;
import com.nixsolutions.entity.UserType;
import com.nixsolutions.logic.ApplicationLogic;
import com.nixsolutions.logic.UserLogic;
import com.nixsolutions.util.Path;

public class EditUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Logger LOGGER = Logger.getLogger(EditUserServlet.class);
	private ApplicationLogic userLogic = new UserLogic();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		LOGGER.trace("Servlet start");

		String login = request.getParameter("login");

		User userProfile = userLogic.findUserByLogin(login);

		UserType userType = UserType.getType(userProfile);

		request.setAttribute("useRole", userType.getName());
		
		LOGGER.info("Update user by  userType.getName() --> " +  userType.getName());


		LOGGER.info("Update user by login --> " + userProfile.getLogin());

		LOGGER.debug("User for update -- > " + userProfile);

		request.setAttribute("action", "edit");

		request.setAttribute("userProfile", userProfile);

		LOGGER.trace("Set the request  attribute: userProfile --> " + userProfile);

		request.getRequestDispatcher(Path.USER_PROFILE).forward(request, response);

		LOGGER.trace("Servlet finish");

	}

}
