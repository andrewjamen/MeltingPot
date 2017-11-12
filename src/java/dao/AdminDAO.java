package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import model.Report;
import model.UserBean;

/**
 *
 * @author Andrew Amen
 */
public class AdminDAO {

    //Database Connection Info
    private final static String CONNECTION_STRING = "jdbc:derby://localhost:1527/MeltingPotLocal";
    private final static String DRIVER_STRING = "org.apache.derby.jdbc.ClientDriver";
    private final static String USERNAME = "melt";
    private final static String PASSWORD = "pot";
    private final static String REPORTS_TABLE = "MELT.REPORTS";
    private final static String USERS = "MELT.USERS";

    public static ArrayList<String> findBannedAccounts() {
        ArrayList<String> bannedAccounts = new ArrayList<>();
        ArrayList<UserBean> allAccounts = UserDAO.findAll();

        for (UserBean aBean : allAccounts) {

            if (aBean.isBanned()) {
                bannedAccounts.add(aBean.getUsername());
            }
        }
        return bannedAccounts;
    }

    public static ArrayList<Report> findAllReports() {

        String query = "SELECT * FROM " + REPORTS_TABLE;
        ArrayList<Report> reports = selectReportsFromDB(query);
        return reports;
    }

    public static ArrayList<Report> selectReportsFromDB(String query) {
        ArrayList<Report> reports = new ArrayList();

        try {
            DBHelper.loadDriver(DRIVER_STRING);
            Connection conn = DBHelper.connect2DB(CONNECTION_STRING, USERNAME, PASSWORD);

            PreparedStatement pstmt = conn.prepareStatement(query);

            ResultSet rs = pstmt.executeQuery();
            String reporter, offender, message;
            int reportID;
            Report aReport;
            while (rs.next()) {
                // 1. if a float (say PRICE) is to be retrieved, use rs.getFloat("PRICE");
                // 2. Instead of using column name, can alternatively use: rs.getString(1); // not 0
                reportID = rs.getInt("reportID");
                reporter = rs.getString("Reporter");
                offender = rs.getString("Offender");
                message = rs.getString("Message");

                // make a Report object out of the values
                aReport = new Report(reportID, reporter, offender, message);
                // add the newly created object to the collection
                reports.add(aReport);
            }

            rs.close();
            pstmt.close();
            conn.close();

        } catch (Exception e) {
            System.err.println("ERROR: Problem with Conversation Selection");
            e.printStackTrace();
        }

        return reports;
    }
    
    public static int banAccount(UserBean profile){
                int rowCount = -1;
        try {
            DBHelper.loadDriver(DRIVER_STRING);
            Connection conn = DBHelper.connect2DB(CONNECTION_STRING, USERNAME, PASSWORD);

            PreparedStatement pstmt = conn.prepareStatement("UPDATE  " + USERS + " SET "
                    + "BANNED = true WHERE USERNAME = ?");
            pstmt.setString(1, profile.getUsername());

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
    
    public static int unbanAccount(UserBean profile){
                int rowCount = -1;
        try {
            DBHelper.loadDriver(DRIVER_STRING);
            Connection conn = DBHelper.connect2DB(CONNECTION_STRING, USERNAME, PASSWORD);

            PreparedStatement pstmt = conn.prepareStatement("UPDATE  " + USERS + " SET "
                    + "BANNED = false WHERE USERNAME = ?");
            pstmt.setString(1, profile.getUsername());

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
