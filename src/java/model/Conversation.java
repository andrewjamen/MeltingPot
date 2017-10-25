/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

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
        this.id = 0;
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
        
        //TODO: If conversation does not exist, create conversation.
    }
    
    /**
     * 
     * @param message
     */
    public void sendMessage(Message message) {
        addMessage(message);
        //TODO: Create message in database.
    }
    
    public void receiveMessages() {
        //TODO: Read new messages from database.
        Message message = new Message();
        
        addMessage(message);
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
}
