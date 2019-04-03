package com.nixsolutions.connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nixsolutions.connection.util.DBResourceManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public final class ConnectionPool {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ConnectionPool.class);
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;
    private static ConnectionPool instance;
    private static AtomicBoolean instanceCreated = new AtomicBoolean(false);
    private static ReentrantLock instanceLock = new ReentrantLock(true);

    public Connection getConnection() throws SQLException {
        LOGGER.trace("get Connection");
        return ds.getConnection();
    }

    private ConnectionPool() {
        initPool();
    }

    public static ConnectionPool getInstance() {
        LOGGER.trace("get pool instance");
        if (!instanceCreated.get()) {
            instanceLock.lock();
            try {
                if (!instanceCreated.get()) {
                    LOGGER.trace("new pool instance");
                    instance = new ConnectionPool();
                    instanceCreated.set(true);
                }
            } finally {
                instanceLock.unlock();
            }
        }
        return instance;
    }

    private void initPool() {
        LOGGER.trace("init pool");
        config.setJdbcUrl(DBResourceManager.getProperty(DBResourceManager.URL));
        config.setUsername(
                DBResourceManager.getProperty(DBResourceManager.USER));
        config.setPassword(
                DBResourceManager.getProperty(DBResourceManager.PASSWORD));
        ds = new HikariDataSource(config);

    }
}