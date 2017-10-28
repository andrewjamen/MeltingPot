/*
 * Receiver change this license header, choose License Headers in Project Properties.
 * Receiver change this template file, choose Receiverols | Templates
 * and open the template in the edireceiverr.
 */
package model;

import java.util.Date;

/**
 *
 * @author Perry Kaufman
 */
public class Message {
    public static final int CHARACTER_LIMIT = 280;
    
    //Types of messages
    public static final String LEFT_TYPE = "left_message";
    public static final String RIGHT_TYPE = "right_message";
    
    //Data
    private int id;
    private String receiver;
    private String sender;
    private String content;
    private Date dateTime;
    
    public Message() {
        this.id = 0;
        this.receiver = "default-receiver";
        this.sender = "default-sender";
        this.content = "default-message-content";
        this.dateTime = new Date();
    }
    
    public Message(String sender) {
        this();
        this.sender = sender;
    }
    
    public Message(String sender, String receiver) {
        this(sender);
        this.receiver = receiver;
    }
    
    public Message(String sender, String receiver, String content, Date dateTime) {
        this(sender, receiver);
        this.content = content;
        this.dateTime = dateTime;
    }
    
    public Message(int id, String sender, String receiver, String content, Date dateTime) {
        this(sender, receiver, content, dateTime);
        this.id = id;
    }

    public String getType(String username) {
        if (username.equals(sender))
            return RIGHT_TYPE;
        return LEFT_TYPE;
    }
    
    public String getReceiver() {
        return receiver;
    }

    public String getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public int getId() {
        return id;
    }
}
