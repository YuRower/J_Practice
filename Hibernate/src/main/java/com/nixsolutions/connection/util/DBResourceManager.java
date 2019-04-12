package com.nixsolutions.connection.util;

import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public final class DBResourceManager {

	private DBResourceManager() {
	}

	private static ResourceBundle resourceBundle;
	private static String property;
	static Logger LOGGER = Logger.getLogger(DBResourceManager.class);

	public static final String URL = "url";
	public static final String USER = "user";
	public static final String PASSWORD = "password";
	public static final String POOL_SIZE = "maximumPoolSize";
	public static final String IDLE_CONNECTIONS = "minimumIdle";

	public static void setProperty(String pathProperty) {
		property = pathProperty;
	}

	public static String getProperty(String key) {
		loadProperties();
		return resourceBundle.getString(key);
	}

	public static void loadProperties() {
		LOGGER.trace("load propertie files");
		if (property == null) {
			property = "database";
		}
		resourceBundle = ResourceBundle.getBundle(property);
		LOGGER.debug("Loaded resource from " + property);

	}

	public static Properties getProperties() {
		LOGGER.trace("load propertie files");
		if (property == null) {
			property = "database.properties";
		}
		Properties properties = new Properties();
		try {
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(property));
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		return properties;
	}

}