/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.Status;

/**
 *
 * @author josh vetter
 */
public class StatusDAO {

    private final static String CONNECTION_STRING = "jdbc:derby://localhost:1527/MeltingPotLocal";
    private final static String DRIVER_STRING = "org.apache.derby.jdbc.ClientDriver";
    private final static String USERNAME = "melt";
    private final static String PASSWORD = "pot";
    private final static String TABLE_NAME = "MELT.Status";

    public static boolean updateStatus(String username, String currentStatus) {
        int rowCount = 0;
        int id = -1;

        try {
            DBHelper.loadDriver(DRIVER_STRING);
            Connection conn = DBHelper.connect2DB(CONNECTION_STRING, USERNAME, PASSWORD);

            PreparedStatement pstmt = conn.prepareStatement("UPDATE " + TABLE_NAME + " SET CURRENTSTATUS = ? WHERE USERNAME = ?");

            pstmt.setString(1, currentStatus);
            pstmt.setString(2, username);

            rowCount = pstmt.executeUpdate();

            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if (rowCount != 1) {
            System.err.println("ERROR: Status update failed.");
            return false;
        }
        return true;

    }

    public static boolean insertStatus(String currentStatus, String username) {
        int rowCount = 0;
        int id = -1;

        try {
            DBHelper.loadDriver(DRIVER_STRING);
            Connection conn = DBHelper.connect2DB(CONNECTION_STRING, USERNAME, PASSWORD);

            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO " + TABLE_NAME + " VALUES(DEFAULT,?,?)", Statement.RETURN_GENERATED_KEYS);
            
            pstmt.setString(1, currentStatus);
            pstmt.setString(2, username);

            rowCount = pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                //Get generated key?
            }

            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if (rowCount != 1) {
            System.err.println("ERROR: Status update failed.");
            return false;
        }
        return true;

    }

    public static String getCurrentStatus(String username) {

        String currentStatus = null;

        try {
            DBHelper.loadDriver(DRIVER_STRING);
            Connection conn = DBHelper.connect2DB(CONNECTION_STRING, USERNAME, PASSWORD);

            PreparedStatement pstmt = conn.prepareStatement("SELECT CURRENTSTATUS FROM " + TABLE_NAME + " WHERE USERNAME = ?");
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                currentStatus = rs.getString("CURRENTSTATUS");
            }

            pstmt.close();
            conn.close();
            rs.close();
        } catch (Exception e) {
            System.err.println("ERROR: get currentStatus problem");
            e.printStackTrace();
        }

        return currentStatus;
    }
}
