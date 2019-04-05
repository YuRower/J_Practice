package com.nixsolutions.connection.util;

import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DBResourceManager {

    private DBResourceManager() {
    }

    private static ResourceBundle resourceBundle;
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

    public static String getProperty(String key) {
        loadProperties();
        return resourceBundle.getString(key);
    }

    private static void loadProperties() {
        LOGGER.trace("load propertie files");
        if (property == null) {
            property = "database";
        }
        resourceBundle = ResourceBundle.getBundle(property);
        LOGGER.debug("Loaded resource from " + property);

    }

}