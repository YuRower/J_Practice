package com.nixsolutions.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

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
import com.nixsolutions.util.Path;

public class SecurityFilter implements Filter {
	private static final Logger LOG = Logger.getLogger(SecurityFilter.class);
	private Map<UserType, List<String>> accessMap = new HashMap<UserType, List<String>>();
	private List<String> outOfControl = new ArrayList<String>();

	public void destroy() {
		LOG.trace("Filter destructed");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		LOG.debug("Filter starts");

		if (accessAllowed(request)) {
			LOG.debug("Filter finished");
			chain.doFilter(request, response);
		} else {
			String errorMessasge = "You do not have permission to access the requested resource";

			request.setAttribute("errorMessage", errorMessasge);
			LOG.trace("Set the request attribute: errorMessage --> " + errorMessasge);

			request.getRequestDispatcher(Path.ERROR_PAGE).forward(request, response);
		}

	}

	private boolean accessAllowed(ServletRequest request) {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String servletPath = httpRequest.getServletPath();

		LOG.debug("Patr is " + servletPath);

		if (servletPath == null || servletPath.isEmpty()) {
			LOG.debug("servletPath is empty");
			return false;
		}

		if (outOfControl.contains(servletPath)) {
			LOG.trace("Page found");
			return true;
		}
		if ((servletPath.startsWith("/js/")) || (servletPath.startsWith("/style/"))) {
			LOG.trace("Config found");
			return true;
		}
		HttpSession session = httpRequest.getSession(false);
		if (session == null) {
			LOG.debug("The session is destroyed or has not been activated");
			return false;
		}

		UserType userType = (UserType) session.getAttribute("userType");
		LOG.debug("User type -->" + userType);

		if (userType == null) {
			LOG.debug("User is not defined");
			return false;
		}
		LOG.debug("Pages for role" + accessMap.get(userType));

		return accessMap.get(userType).contains(servletPath);

	}

	public void init(FilterConfig fConfig) throws ServletException {
		LOG.trace("Filter initialization starts");

		accessMap.put(UserType.ADMIN, asList(fConfig.getInitParameter("admin")));
		accessMap.put(UserType.USER, asList(fConfig.getInitParameter("user")));

		LOG.debug("Access map --> " + accessMap);

		outOfControl = asList(fConfig.getInitParameter("out-of-control"));

		LOG.debug("Out of control commands --> " + outOfControl);

		LOG.trace("Filter initialization finished");
	}

	private List<String> asList(String str) {
		List<String> list = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(str);
		while (st.hasMoreTokens()) {
			list.add(st.nextToken());
		}
		return list;
	}
}
