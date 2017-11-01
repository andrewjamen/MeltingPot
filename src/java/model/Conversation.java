/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import dao.ConversationDAO;
import java.util.ArrayList;

/**
 *
 * @author Perry Kaufman
 */
public class Conversation {

    public static final int MAX_HISTORY = 20;

    private int id;
    private ArrayList<Message> messages;
    private String username;
    private String partnerUsername;

    public Conversation() {
        this.id = -1;
        this.messages = new ArrayList();
        //TODO: Values for testing only. Change later.
        this.username = "default-sender";
        this.partnerUsername = "default-receiver";
    }

    public Conversation(String username, String partnerUsername) {
        this();
        this.username = username;
        this.partnerUsername = partnerUsername;
        this.getConversationIfExists();
    }

    /**
     * Add new message to array and database from the user.
     *
     * @param message
     */
    public void sendMessage(Message message) {
        try {
            if (this.id < 0 && !getConversationIfExists()) {
                this.createConversation();
            }
        } catch (Exception e) {
            System.err.println(e);
            return; //Message not sent.
        }

        //Add message to database.
        message = ConversationDAO.addMessage(this.id, message);

        if (message == null) return; //Error
        
        //Add message to array.
        this.addMessage(message);
        this.limitHistory();
    }

    /**
     * Gets a conversation and its messages from the database if it exists.
     * Returns true if conversation found and false otherwise.
     *
     * @return
     */
    private boolean getConversationIfExists() {
        this.id = ConversationDAO.getConversation(username, partnerUsername);
        if (id > -1) {
            this.messages = ConversationDAO.getAllMessages(id);
            return true;
        }
        return false;
    }

    /**
     * Creates a conversation.
     *
     * @throws Exception If conversation could not be created.
     */
    private void createConversation() throws Exception {
        this.id = ConversationDAO.createConversation(username, partnerUsername);
        if (id < 0) {
            throw new Exception("Conversation not created.");
        }
    }

    /**
     * Receives new messages if they exist in the database.
     * @return true if new messages, false otherwise.
     */
    public boolean receiveMessages() {
        //addMessage(new Message()); //Uncomment to test poll

        //Read new messages from database;
        ArrayList<Message> receivedMessages = ConversationDAO.getNewMessagesTo(this.id, this.getLastIDReceived(), this.username);
        if (receivedMessages == null || receivedMessages.isEmpty()) {
            return false;
        }

        //Add new messages to array.
        for (int i = 0; i < receivedMessages.size(); i++) {
            addMessage(receivedMessages.get(i));
            this.limitHistory();
        }
        return true;
    }
    
    private void limitHistory() {
        //TODO: Make this more efficient for multiple deletions.
        if (messages.size() > MAX_HISTORY) {
            Message message = messages.remove(0);
            ConversationDAO.deleteMessageByID(message.getId());
        }
    }

    /**
     * Adds a message to the list.
     *
     * @param message
     */
    private void addMessage(Message message) {
        messages.add(message);
    }

    private int getLastIDReceived() {
        for (int i = messages.size() - 1; i > -1; i--) {
            Message current = messages.get(i);
            if (current.getSender().equals(partnerUsername)) {
                return current.getId();
            }
        }

        return -1;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPartnerUsername() {
        return partnerUsername;
    }
}
