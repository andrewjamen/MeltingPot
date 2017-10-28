/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import model.Message;

/**
 *
 * @author Perry
 */
public class ConversationDAO {

    //Database Connection Info
    private final static String CONNECTION_STRING = "jdbc:derby://localhost:1527/MeltingPotLocal";
    private final static String DRIVER_STRING = "org.apache.derby.jdbc.ClientDriver";
    private final static String USERNAME = "melt";
    private final static String PASSWORD = "pot";

    //Conversation Table Table and Column Names
    private final static String C_TABLE_NAME = "MELT.Conversations";
    private final static String C_CONV_ID = "CONV_ID";
    private final static String C_USER_A = "USERNAME_A";
    private final static String C_USER_B = "USERNAME_B";

    //Message Table Table and Column Names
    private final static String M_TABLE_NAME = "MELT.Messages";
    private final static String M_CONV_ID = "CONV_ID";
    private final static String M_MESG_ID = "MESSAGE_ID";
    private final static String M_SENDER = "SENDER";
    private final static String M_RECEIVER = "RECEIVER";
    private final static String M_DATETIME = "DATETIME";

    public static int getConversation(String username, String partnerUsername) {
        int conv_id = -1;

        try {
            DBHelper.loadDriver(DRIVER_STRING);
            Connection conn = DBHelper.connect2DB(CONNECTION_STRING, USERNAME, PASSWORD);

            ArrayList<String> orderedList = determineOrder(username, partnerUsername);

            PreparedStatement pstmt = conn.prepareStatement("SELECT " + C_CONV_ID + " FROM " + C_TABLE_NAME + " WHERE " + C_USER_A + " = ? AND " + C_USER_B + " = ?");

            pstmt.setString(1, orderedList.get(0));
            pstmt.setString(2, orderedList.get(1));

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                conv_id = rs.getInt(C_CONV_ID);
            }

        } catch (Exception e) {
            System.err.println("ERROR: Problem with Conversation Selection");
            e.printStackTrace();
        }

        return conv_id;
    }

    public static int createConversation(String username, String partnerUsername) {
        int rowCount = 0;
        try {
            DBHelper.loadDriver(DRIVER_STRING);
            Connection conn = DBHelper.connect2DB(CONNECTION_STRING, USERNAME, PASSWORD);

            ArrayList<String> orderedList = determineOrder(username, partnerUsername);

            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO " + C_TABLE_NAME + " VALUES(DEFAULT,?,?)");
            pstmt.setString(1, orderedList.get(0));
            pstmt.setString(2, orderedList.get(1));

            rowCount = pstmt.executeUpdate();
            
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            System.err.println("ERROR: Problem with Conversation Insertion");
            e.printStackTrace();
        }
        if (rowCount == 1) {
            return getConversation(username, partnerUsername);
        }
        return -1;
    }

    public static boolean deleteConversationByID(int ID) {

        return false;
    }

    public static ArrayList getMessages(int convID, int lastMessageID) {

        return null;
    }

    public static boolean addMessage(int convID, Message message) {
        int rowCount = 0;
        try {
            DBHelper.loadDriver(DRIVER_STRING);
            Connection conn = DBHelper.connect2DB(CONNECTION_STRING, USERNAME, PASSWORD);
            
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO " + M_TABLE_NAME + " VALUES(DEFAULT,?,?,?,?,?)");
            pstmt.setInt(1, convID);
            pstmt.setString(2, message.getSender());
            pstmt.setString(3, message.getReceiver());
            pstmt.setTimestamp(4, new Timestamp(message.getDateTime().getTime()));
            pstmt.setString(5, message.getContent());
            
            rowCount = pstmt.executeUpdate();
            
        }catch (Exception e) {
            System.err.println("ERROR: Problem with Message Insertion");
            e.printStackTrace();
        }
        if (rowCount == 1) return true;
        return false;
    }

    private static ArrayList<String> determineOrder(String str_1, String str_2) {
        ArrayList<String> orderedList = new ArrayList();

        if (str_1.compareTo(str_2) < 0) {
            orderedList.add(str_1);
            orderedList.add(str_2);
        } else {
            orderedList.add(str_2);
            orderedList.add(str_1);
        }

        return orderedList;
    }
}
