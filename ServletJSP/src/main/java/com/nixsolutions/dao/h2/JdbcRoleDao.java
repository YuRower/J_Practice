package com.nixsolutions.dao.h2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.nixsolutions.dao.AbstractJdbcDao;
import com.nixsolutions.dao.RoleDao;
import com.nixsolutions.entity.Role;
import com.nixsolutions.exception.DBException;
import com.nixsolutions.exception.Messages;

public class JdbcRoleDao extends AbstractJdbcDao implements RoleDao {
    private static final Logger LOG = Logger
            .getLogger(JdbcRoleDao.class);
    public static final String SQL_CREATE_ROLE = " INSERT INTO LABA13_DB.ROLE VALUES(DEFAULT,?)";
    public static final String SQL_UPDATE_ROLE = "UPDATE LABA13_DB.ROLE Set name = ? WHERE role_id = ?";
    public static final String SQL_REMOVE_ROLE = "DELETE FROM LABA13_DB.ROLE WHERE name = ?";
    public static final String SQL_FIND_ROLE_BY_NAME = "SELECT role_id, name from LABA13_DB.ROLE WHERE name = ?";

    private static final String ROLE_ID = "role_id";
    private static final String ROLE_NAME = "name";

    
    public void create(Role role) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = createConnection();
            pstmt = conn.prepareStatement(SQL_CREATE_ROLE,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, role.getName());
            pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_CREATE_ROLE, ex);
            rollbackConnection(conn);
            throw new DBException(Messages.ERR_CANNOT_CREATE_ROLE, ex);
        } finally {
            close(conn, pstmt, rs);

        }
    }

    
    public void update(Role role) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = createConnection();
            pstmt = conn.prepareStatement(SQL_UPDATE_ROLE);
            pstmt.setString(1, role.getName());
            pstmt.setLong(2, role.getId());

            pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_UPDATE_ROLE, ex);
            rollbackConnection(conn);
            throw new DBException(Messages.ERR_CANNOT_UPDATE_ROLE, ex);
        } finally {
            close(pstmt);
            close(conn);

        }
    }

    
    public void remove(Role role) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = createConnection();
            pstmt = conn.prepareStatement(SQL_REMOVE_ROLE);
            pstmt.setString(1, role.getName());
            pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_REMOVE_ROLE, ex);
            rollbackConnection(conn);
            throw new DBException(Messages.ERR_CANNOT_REMOVE_ROLE, ex);
        } finally {
            close(pstmt);
            close(conn);

        }
    }

    
    public Role findByName(String name) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Role role = null;
        try {
            conn = createConnection();
            pstmt = conn.prepareStatement(SQL_FIND_ROLE_BY_NAME);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                role = new Role();
                role.setId(rs.getLong(ROLE_ID));
                role.setName(rs.getString(ROLE_NAME));
            }
            conn.commit();
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_FIND_ROLE, ex);
            rollbackConnection(conn);
            throw new DBException(Messages.ERR_CANNOT_FIND_ROLE, ex);
        } finally {
            close(conn, pstmt, rs);

        }
        return role;

    }
}
