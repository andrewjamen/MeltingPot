/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import model.Message;

/**
 *
 * @author Perry Kaufman
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

    //Message Table and Column Names
    private final static String M_TABLE_NAME = "MELT.Messages";
    private final static String M_CONV_ID = "CONV_ID";
    private final static String M_MESG_ID = "MESSAGE_ID";
    private final static String M_SENDER = "SENDER";
    private final static String M_RECEIVER = "RECEIVER";
    private final static String M_CONTENT = "CONTENT";
    private final static String M_DATETIME = "DATETIME";

    /**
     * Returns the conversation id of a conversation between two users if it
     * exists. Returns -1 otherwise.
     *
     * @param username
     * @param partnerUsername
     * @return
     */
    public static int getConversation(String username, String partnerUsername) {
        int conv_id = -1;

        try {
            DBHelper.loadDriver(DRIVER_STRING);
            Connection conn = DBHelper.connect2DB(CONNECTION_STRING, USERNAME, PASSWORD);

            ArrayList<String> orderedList = sortStringPair(username, partnerUsername);

            PreparedStatement pstmt = conn.prepareStatement("SELECT " + C_CONV_ID + " FROM " + C_TABLE_NAME + " WHERE " + C_USER_A + " = ? AND " + C_USER_B + " = ?");

            pstmt.setString(1, orderedList.get(0));
            pstmt.setString(2, orderedList.get(1));

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                conv_id = rs.getInt(C_CONV_ID);
            }

            pstmt.close();
            conn.close();
            rs.close();
        } catch (Exception e) {
            System.err.println("ERROR: Problem with Conversation Selection");
            e.printStackTrace();
        }

        return conv_id;
    }

    /**
     * Creates a conversation between two users and returns the conversation id.
     * Returns -1 if this fails.
     *
     * @param username
     * @param partnerUsername
     * @return
     */
    public static int createConversation(String username, String partnerUsername) {
        int rowCount = 0;
        try {
            DBHelper.loadDriver(DRIVER_STRING);
            Connection conn = DBHelper.connect2DB(CONNECTION_STRING, USERNAME, PASSWORD);

            ArrayList<String> orderedList = sortStringPair(username, partnerUsername);

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

    /**
     * Deletes a conversation by conversation id.
     *
     * @param id
     * @return
     */
    public static boolean deleteConversationByID(int convID) {
        //TODO
        return false;
    }

    /**
     * Deletes messages by conversation id.
     *
     * @param id
     * @return
     */
    public static boolean deleteMessagesByConversationID(int convID) {
        //TODO
        return false;
    }

    /**
     * Deletes a message by message id.
     *
     * @param id
     * @return
     */
    public static boolean deleteMessageByID(int mesgID) {
        int rowCount = -1;
        
        try {
            DBHelper.loadDriver(DRIVER_STRING);
            Connection conn = DBHelper.connect2DB(CONNECTION_STRING, USERNAME, PASSWORD);
            
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM " + M_TABLE_NAME + " WHERE " + M_MESG_ID + " = ?");
            pstmt.setInt(1, mesgID);
            
            rowCount = pstmt.executeUpdate();
            System.out.println("DELETE " + mesgID);
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            System.err.println("ERROR: Problem with Message Delete");
            e.printStackTrace();
        }
        if (rowCount > 0) return true;
        return false;
    }

    /**
     * Gets all messages sent to a specified user after a specified message id.
     *
     * @param convID
     * @param lastMessageID
     * @param username
     * @return
     */
    public static ArrayList getNewMessagesTo(int convID, int lastMessageID, String username) {
        ArrayList<Message> messages = new ArrayList();
        try {
            DBHelper.loadDriver(DRIVER_STRING);
            Connection conn = DBHelper.connect2DB(CONNECTION_STRING, USERNAME, PASSWORD);

            //TODO: Must also filter query by sender or receiver.
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM " + M_TABLE_NAME + " WHERE " + M_CONV_ID + " = ? AND " + M_MESG_ID + " > ? AND " + M_RECEIVER + " = ? ORDER BY " + M_MESG_ID);
            pstmt.setInt(1, convID);
            pstmt.setInt(2, lastMessageID);
            pstmt.setString(3, username);

            ResultSet rs = pstmt.executeQuery();

            messages = processMessageResultSet(rs);

            pstmt.close();
            conn.close();
            rs.close();
        } catch (Exception e) {
            System.err.println("ERROR: Problem with Message Select");
            e.printStackTrace();
        }
        return messages;
    }

    /**
     * Gets all messages of a conversation.
     *
     * @param convID
     * @return
     */
    public static ArrayList getAllMessages(int convID) {
        ArrayList<Message> messages = new ArrayList();
        try {
            DBHelper.loadDriver(DRIVER_STRING);
            Connection conn = DBHelper.connect2DB(CONNECTION_STRING, USERNAME, PASSWORD);

            //TODO: Must also filter query by sender or receiver.
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM " + M_TABLE_NAME + " WHERE " + M_CONV_ID + " = ? ORDER BY " + M_MESG_ID);
            pstmt.setInt(1, convID);

            ResultSet rs = pstmt.executeQuery();

            messages = processMessageResultSet(rs);

            pstmt.close();
            conn.close();
            rs.close();
        } catch (Exception e) {
            System.err.println("ERROR: Problem with Message Select");
            e.printStackTrace();
        }
        return messages;
    }

    /**
     * Adds a message to a conversation of a specified id.
     *
     * @param convID
     * @param message
     * @return
     */
    public static Message addMessage(int convID, Message message) {
        int rowCount = -1;
        try {
            DBHelper.loadDriver(DRIVER_STRING);
            Connection conn = DBHelper.connect2DB(CONNECTION_STRING, USERNAME, PASSWORD);

            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO " + M_TABLE_NAME + " VALUES(DEFAULT,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, convID);
            pstmt.setString(2, message.getSender());
            pstmt.setString(3, message.getReceiver());
            pstmt.setTimestamp(4, new Timestamp(message.getDateTime().getTime()));
            pstmt.setString(5, message.getContent());

            rowCount = pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            
            if (rs.next()) {
                message.setId(rs.getInt(1));
            }
            
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            System.err.println("ERROR: Problem with Message Insertion");
            e.printStackTrace();
        }
        if (rowCount != 1) {
            System.err.println("ERROR: Message Insertion failed.");
            return null;
        }
        return message;
    }

    /**
     * Processes a ResultSet into an ArrayList of messages.
     *
     * @param rs
     * @return
     * @throws Exception
     */
    private static ArrayList<Message> processMessageResultSet(ResultSet rs) throws Exception {
        ArrayList<Message> messages = new ArrayList();

        while (rs.next()) {
            int id = rs.getInt(M_MESG_ID);
            String sender = rs.getString(M_SENDER);
            String receiver = rs.getString(M_RECEIVER);
            String content = rs.getString(M_CONTENT);
            Date dateTime = new Date(rs.getTimestamp(M_DATETIME).getTime());
            messages.add(new Message(id, sender, receiver, content, dateTime));
        }

        return messages;
    }

    /**
     * Returns an ArrayList containing the two strings in lexicographical order.
     *
     * @param str_1
     * @param str_2
     * @return
     */
    private static ArrayList<String> sortStringPair(String str_1, String str_2) {
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
