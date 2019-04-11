package com.nixsolutions.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.nixsolutions.entity.UserType;
import com.nixsolutions.logic.ApplicationLogic;
import com.nixsolutions.logic.UserLogic;

public class AdminResource implements Filter {
	private static final Logger LOG = Logger.getLogger(AdminResource.class);

	public void destroy() {
		LOG.trace("Filter destruction");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		LOG.trace("Filter starts");
		HttpServletRequest req = (HttpServletRequest) request;
		chain.doFilter(request, response);
		HttpSession session = req.getSession(false);
		if (session != null) {
			if (session.getAttribute("userList") == null) {
				UserType type = (UserType) session.getAttribute("userType");
				if (type == UserType.ADMIN) {
					LOG.debug("User list for role admin not found");
					ApplicationLogic userLogic = new UserLogic();
					session.setAttribute("userList", userLogic.findAll());
					return;
				}
			}
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
		LOG.trace("Filter initialization starts");

	}
}
