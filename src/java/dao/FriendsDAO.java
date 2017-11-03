package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import model.Message;
import model.UserBean;

/**
 *
 * @author Andrew Amen
 */
public class FriendsDAO {

    //Database Connection Info
    private final static String CONNECTION_STRING = "jdbc:derby://localhost:1527/MeltingPotLocal";
    private final static String DRIVER_STRING = "org.apache.derby.jdbc.ClientDriver";
    private final static String USERNAME = "melt";
    private final static String PASSWORD = "pot";

    //User Table Table and Column Names
    private final static String TABLE_NAME = "MELT.Users";
    private final static String LIST = "FriendList";
    private final static String REQUESTS = "FriendRequest";

    public static int sendFriendRequest(UserBean profile, String requests) {
        int rowCount = -1;
        try {
            DBHelper.loadDriver(DRIVER_STRING);
            Connection conn = DBHelper.connect2DB(CONNECTION_STRING, USERNAME, PASSWORD);

            PreparedStatement pstmt = conn.prepareStatement("UPDATE  " + TABLE_NAME + "  SET "
                    + REQUESTS + " = ? WHERE USERNAME = ?");
            pstmt.setString(1, requests);
            pstmt.setString(2, profile.getUsername());

            rowCount = pstmt.executeUpdate();

            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if (rowCount != 1) {
            System.err.println("ERROR: Message Insertion failed.");
        }
        return rowCount;

    }

    public static int addFriend(UserBean profile, String friends) {
        int rowCount = -1;
        try {
            DBHelper.loadDriver(DRIVER_STRING);
            Connection conn = DBHelper.connect2DB(CONNECTION_STRING, USERNAME, PASSWORD);

            PreparedStatement pstmt = conn.prepareStatement("UPDATE  " + TABLE_NAME + "  SET "
                    + LIST + " = ? WHERE USERNAME = ?");
            pstmt.setString(1, friends);
            pstmt.setString(2, profile.getUsername());

            rowCount = pstmt.executeUpdate();

            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if (rowCount != 1) {
            System.err.println("ERROR: Message Insertion failed.");
        }
        return rowCount;
    }

    public static int removeRequest(UserBean profile, String requests) {
        int rowCount = -1;
        try {
            DBHelper.loadDriver(DRIVER_STRING);
            Connection conn = DBHelper.connect2DB(CONNECTION_STRING, USERNAME, PASSWORD);

            PreparedStatement pstmt = conn.prepareStatement("UPDATE  " + TABLE_NAME + "  SET "
                    + REQUESTS + " = ? WHERE USERNAME = ?");
            pstmt.setString(1, requests);
            pstmt.setString(2, profile.getUsername());

            rowCount = pstmt.executeUpdate();

            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if (rowCount != 1) {
            System.err.println("ERROR: Message Insertion failed.");
        }
        return rowCount;

    }
}
