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
 * @author Perry
 */
public class Conversation {
    private int id;
    private ArrayList<Message> messages;
    String username;
    String partnerUsername;

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
        //TODO: Get existing conversation from database if exists.
        this.getConversationIfExists();
    }
    
    /**
     * Add new message to array and database from the user.
     * @param message
     */
    public void sendMessage(Message message) {
        try {
            if (this.id < 0 && !getConversationIfExists()) this.createConversation();
        } catch (Exception e) {
            System.err.println(e);
            return; //Message not sent.
        }
        
        //Add message to database.
        ConversationDAO.addMessage(this.id, message);
        
        //Add message to array.
        addMessage(message);
    }
    
    public final boolean getConversationIfExists() {
        this.id = ConversationDAO.getConversation(username, partnerUsername);
        if (id > -1)  {
            this.messages = ConversationDAO.getMessages(id, id);
            return true;
        }
        return false;
    }

    public void createConversation() throws Exception{
        this.id = ConversationDAO.createConversation(username, partnerUsername);
        if (id < 0) throw new Exception("Conversation not created.");
    }
    
    /**
     * Check database for new messages to be received.
     */
    public void receiveMessages() {
        //addMessage(new Message()); //Uncomment to test poll
        
        //Read new messages from database;
        ArrayList<Message> receivedMessages = ConversationDAO.getMessages(this.id, this.getLastIDReceived());
        if (receivedMessages == null) return;
        
        //Add new messages to array.
        for (int i = 0; i < receivedMessages.size(); i++) {
            addMessage(receivedMessages.get(i));
        }
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
    
    public void addMessage(Message message) {
        messages.add(message);
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
    
    private int getLastIDReceived() {
        for (int i = messages.size() - 1; i > -1 ; i--) {
            Message current = messages.get(i);
            if (current.getSender().equals(partnerUsername)) return current.getId();
        }
        
        return -1;
    }
}
