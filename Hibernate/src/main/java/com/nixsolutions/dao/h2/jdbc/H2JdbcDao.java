package com.nixsolutions.dao.h2.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.nixsolutions.connection.ConnectionPool;
import com.nixsolutions.dao.AbstractDAOFactory;
import com.nixsolutions.dao.RoleDao;
import com.nixsolutions.dao.UserDao;
import com.nixsolutions.exception.ConnectionException;
import com.nixsolutions.exception.Messages;

public class H2JdbcDao extends AbstractDAOFactory {

	private static final Logger LOGGER = Logger.getLogger(H2JdbcDao.class);
	private static H2JdbcDao instance;

	public synchronized static H2JdbcDao getInstance() {
		if (instance == null) {
			instance = new H2JdbcDao();
		}
		return instance;

	}

	public Connection createConnection() {
		try {
			Connection connection = ConnectionPool.getInstance().getConnection();
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

	@Override
	public UserDao getUserDAO() {
		return new JdbcUserDao();
	}

	@Override
	public RoleDao getRoleDAO() {
		return new JdbcRoleDao();
	}

}
