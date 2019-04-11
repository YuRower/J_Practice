package com.nixsolutions.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.nixsolutions.connection.ConnectionPool;

public class ContextListener implements ServletContextListener {

	private static final Logger LOGGER = Logger.getLogger(ContextListener.class);

	public void contextDestroyed(ServletContextEvent arg0) {
		LOGGER.trace("Servlet context destruction");
	}

	public void contextInitialized(ServletContextEvent arg0) {
		LOGGER.trace("Servlet context initialization started");
		LOGGER.debug("Init data source");
		initDriver();
		initDataSource();
		LOGGER.trace("Servlet context initialization finished");
	}

	private void initDataSource() {
		ConnectionPool.getInstance();
	}

	private void initDriver() {
		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException ex) {
			throw new IllegalStateException("Cannot initialize Command Container");
		}
	}

}
