package com.nixsolutions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.nixsolutions.dao.AbstractJdbcDao;

public class Main extends AbstractJdbcDao {

    public static void main(String[] args) {
        String sql = "DROP TABLE IF EXISTS TEST;" + " CREATE TABLE  TEST "
                + "(id INTEGER not NULL, " + " first VARCHAR(255), "
                + " last VARCHAR(255), " + " age INTEGER, "
                + " PRIMARY KEY ( id ))";
        Main m = new Main();
        m.create(sql);
    }

    public void create(String str) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = createConnection();
            pstmt = conn.prepareStatement(str);
            pstmt.execute();

            conn.commit();
        } catch (SQLException ex) {
            rollbackConnection(conn);
            ex.printStackTrace();
        } finally {
            close(conn, pstmt, rs);

        }
    }
}