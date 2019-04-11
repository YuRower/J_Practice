package com.nixsolutions.dao.h2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.nixsolutions.dao.AbstractJdbcDao;
import com.nixsolutions.dao.UserDao;
import com.nixsolutions.entity.User;
import com.nixsolutions.exception.DBException;
import com.nixsolutions.exception.Messages;

public class JdbcUserDao extends AbstractJdbcDao implements UserDao {
	private static final Logger LOG = Logger.getLogger(JdbcUserDao.class);
	private static final String SQL_FIND_ALL_USER = "SELECT * FROM LABA13_DB.user";
	private static final String SQL_INSERT_USER = "INSERT INTO LABA13_DB.user "
			+ "VALUES (DEFAULT, ?, ?, ?, ? ,? , ?,?)";
	private static final String SQL_REMOVE_USER = "DELETE FROM LABA13_DB.user where user_id = ?";
	private static final String SQL_UPDATE_USER = "UPDATE LABA13_DB.user SET  password = ?, email =  ?, "
			+ "firstName = ? , lastName = ?, birthday = ?, id_role= ? WHERE user_id = ?";
	private static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM LABA13_DB.user WHERE login = ? ";
	private static final String SQL_FIND_USER_BY_EMAIL = "SELECT * FROM LABA13_DB.user WHERE email = ? ";

	private static final String USER_ID = "user_id";
	private static final String USER_LOGIN = "login";
	private static final String USER_PASSWORD = "password";
	private static final String USER_EMAIL = "email";
	private static final String USER_FIRST_NAME = "firstName";
	private static final String USER_LAST_NAME = "lastName";
	private static final String BIRTHDAY = "birthday";
	private static final String USER_ROLE_ID = "id_role";

	public void create(User user) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = createConnection();
			pstmt = conn.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, user.getLogin());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getEmail());
			pstmt.setString(4, user.getFirstName());
			pstmt.setString(5, user.getLastName());
			pstmt.setDate(6, new java.sql.Date(user.getBirthday().getTime()));
			pstmt.setLong(7, user.getRoleId());
			LOG.info(user.getRoleId() + " ");
			pstmt.executeUpdate();
			/*
			 * rs = pstmt.getGeneratedKeys(); if (rs.next()) {
			 * user.setId(rs.getLong(USER_ID)); } }
			 */
			conn.commit();
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_CREATE_USER, ex);
			rollbackConnection(conn);
			throw new DBException(Messages.ERR_CANNOT_CREATE_USER, ex);
		} finally {
			close(conn, pstmt, rs);
		}
	}

	public void update(User user) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = createConnection();
			pstmt = conn.prepareStatement(SQL_UPDATE_USER);
			// pstmt.setString(1, user.getLogin());
			pstmt.setString(1, user.getPassword());
			pstmt.setString(2, user.getEmail());
			pstmt.setString(3, user.getFirstName());
			pstmt.setString(4, user.getLastName());
			pstmt.setDate(5, new java.sql.Date(user.getBirthday().getTime()));
			pstmt.setLong(6, user.getRoleId());
			pstmt.setLong(7, user.getId());
			pstmt.executeUpdate();
			conn.commit();
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_UPDATE_USER, ex);
			rollbackConnection(conn);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_USER, ex);
		} finally {
			close(pstmt);
			close(conn);
		}
	}

	public void remove(User user) {
		LOG.info(user.getId() + "");
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = createConnection();
			pstmt = conn.prepareStatement(SQL_REMOVE_USER);
			pstmt.setLong(1, user.getId());
			pstmt.executeUpdate();
			conn.commit();
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_REMOVE_USER, ex);
			rollbackConnection(conn);
			throw new DBException(Messages.ERR_CANNOT_REMOVE_USER, ex);
		} finally {
			close(pstmt);
			close(conn);
		}
	}

	public List<User> findAll() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<User> list = new ArrayList<User>();
		try {
			conn = createConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL_FIND_ALL_USER);
			while (rs.next()) {
				list.add(extractUser(rs));
			}
			conn.commit();
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_FIND_USERS, ex);
			rollbackConnection(conn);
			throw new DBException(Messages.ERR_CANNOT_FIND_USERS, ex);
		} finally {
			close(conn, stmt, rs);
		}
		return list;
	}

	private User extractUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getLong(USER_ID));
		user.setLogin(rs.getString(USER_LOGIN));
		user.setPassword(rs.getString(USER_PASSWORD));
		user.setEmail(rs.getString(USER_EMAIL));
		user.setFirstName(rs.getString(USER_FIRST_NAME));
		user.setLastName(rs.getString(USER_LAST_NAME));
		user.setBirthday(rs.getDate((BIRTHDAY)));
		user.setRoleId(rs.getLong(USER_ROLE_ID));
		return user;
	}

	public User findByLogin(String login) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user = null;
		try {
			conn = createConnection();
			pstmt = conn.prepareStatement(SQL_FIND_USER_BY_LOGIN);
			pstmt.setString(1, login);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = extractUser(rs);
			}
			conn.commit();
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_FIND_USER_BY_LOGIN, ex);
			rollbackConnection(conn);
			throw new DBException(Messages.ERR_CANNOT_FIND_USER_BY_LOGIN, ex);
		} finally {
			close(conn, pstmt, rs);
		}
		return user;
	}

	public User findByEmail(String email) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user = null;
		try {
			conn = createConnection();
			pstmt = conn.prepareStatement(SQL_FIND_USER_BY_EMAIL);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = extractUser(rs);
			}
			conn.commit();
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_FIND_USER_BY_EMAIL, ex);
			rollbackConnection(conn);
			throw new DBException(Messages.ERR_CANNOT_FIND_USER_BY_EMAIL, ex);
		} finally {
			close(conn, pstmt, rs);
		}
		return user;
	}

}
