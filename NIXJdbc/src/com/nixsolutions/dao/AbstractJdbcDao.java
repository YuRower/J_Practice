package com.nixsolutions.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nixsolutions.connection.ConnectionPool;
import com.nixsolutions.exception.ConnectionException;
import com.nixsolutions.exception.Messages;

public abstract class AbstractJdbcDao {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(AbstractJdbcDao.class);

    public Connection createConnection() {
        try {
            Connection connection = ConnectionPool.getInstance()
                    .getConnection();
            return connection;
        } catch (SQLException e) {
            LOGGER.error("Connection error", e);
            throw new ConnectionException("Connection error", e);
        }
    }

    public void close(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                LOGGER.error(Messages.ERR_CANNOT_CLOSE_CONNECTION, ex);
            }
        }
    }

    public void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                LOGGER.error(Messages.ERR_CANNOT_CLOSE_STATEMENT, ex);
            }
        }
    }

    public void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                LOGGER.error(Messages.ERR_CANNOT_CLOSE_RESULTSET, ex);
            }
        }
    }

    public void close(Connection con, Statement stmt, ResultSet rs) {
        close(rs);
        close(stmt);
        close(con);
    }

    public void rollbackConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                LOGGER.error(Messages.ERR_CANNOT_ROLLBACK_CONNECTION, e);
            }
        }

    }

}
