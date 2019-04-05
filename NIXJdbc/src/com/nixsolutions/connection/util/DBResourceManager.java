package com.nixsolutions.connection.util;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DBResourceManager {

    private DBResourceManager() {
    }

    private static String property;
    private static final Logger LOGGER = LoggerFactory
            .getLogger(DBResourceManager.class);

    public static final String URL = "url";
    public static final String USER = "user";
    public static final String PASSWORD = "password";
    public static final String POOL_SIZE = "maximumPoolSize";
    public static final String IDLE_CONNECTIONS = "minimumIdle";

    public static void setProperty(String pathProperty) {
        property = pathProperty;
    }

    private static Properties loadProperties() {
        LOGGER.trace("load propertie files");
        Properties properties = new Properties();
        if (property == null) {
            property = "resources/database.properties";
        }
        try {
            properties.load(Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream(property));
            return properties;
        } catch (IOException e) {
            LOGGER.error("Exception while loading DB properties file", e);
            throw new RuntimeException(e);
        }

    }

    public static String getProperty(String key) {
        Properties properties = loadProperties();
        if (properties.isEmpty()) {
            LOGGER.error("properties are empty");
            throw new RuntimeException("properties are empty");
        }

        return properties.getProperty(key);
    }
}