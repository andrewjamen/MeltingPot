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
import java.util.ArrayList;
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
    
    
    
    public static int changeStatus(Status status) {
        int rowCount = -1;
        try {
            DBHelper.loadDriver(DRIVER_STRING);
            Connection conn = DBHelper.connect2DB(CONNECTION_STRING, USERNAME, PASSWORD);

            String statusString = "UPDATE " + TABLE_NAME + " SET "
                    + "Status = '" + status.getStatus() + "', "
                    + "DATETIME = '" + status.getDateTime() + "' "
                    + "WHERE UserName = '" + status.getUsername() + "'";

            PreparedStatement pstmt = conn.prepareStatement(statusString);

            rowCount = pstmt.executeUpdate();

            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if (rowCount != 1) {
            System.err.println("ERROR: Status update failed.");
        }
        return rowCount;

    }
    
    
    
}


