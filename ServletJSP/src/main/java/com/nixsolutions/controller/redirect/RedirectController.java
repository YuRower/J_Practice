package com.nixsolutions.controller.redirect;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RedirectController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String page = request.getParameter("page");

		// LOG.trace("Request parameter: command --> " + commandName);
		String redirect = PageContainer.get(page);

		// LOG.trace("Obtained command --> " + command);
		// request.getRequestDispatcher(redirect).forward(request, response);

		response.sendRedirect(request.getContextPath() + redirect);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
