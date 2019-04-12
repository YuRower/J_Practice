package com.nixsolutions.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.nixsolutions.entity.User;
import com.nixsolutions.logic.ApplicationLogic;
import com.nixsolutions.logic.UserLogic;
import com.nixsolutions.util.Path;
import com.nixsolutions.util.UserFormProccess;

public class CreateServlet extends HttpServlet {

	private static Logger LOGGER = Logger.getLogger(CreateServlet.class);
	private static final long serialVersionUID = 1L;
	private ApplicationLogic userLogic = new UserLogic();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		LOGGER.trace("Servlet starts");

		User user = UserFormProccess.proccessForm(request);

		userLogic.newUserWithDefaultValue(user);

		LOGGER.info("Created With Default Value user --> " + user);

		session.setAttribute("action_command", "Create user command");

		LOGGER.debug("Redirect to  --> " + request.getContextPath() + Path.STATUS_PAGE);

		response.sendRedirect(request.getContextPath() + Path.STATUS_PAGE);

		LOGGER.trace("Servlet finished");

	}

}