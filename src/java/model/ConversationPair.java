/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Perry
 */
public class ConversationPair {
    private int id;
    private String username;
    private String partnerUsername;

    public ConversationPair(String username, String partnerUsername) {
        this.id = -1;
        this.username = username;
        this.partnerUsername = partnerUsername;
    }
    
    public ConversationPair(int id, String username, String partnerUsername) {
        this(username, partnerUsername);
        this.id = id;
    }
    
    @Override
    public String toString() {
        return id + " " + username + " " + partnerUsername;
    }

    public int getId() {
        return this.id;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPartnerUsername() {
        return partnerUsername;
    }

    public void setPartner(String partnerUsername) {
        this.partnerUsername = partnerUsername;
    }
    
    public String getFirst() {
        if (username.compareTo(partnerUsername) < 0) return username;
        return partnerUsername;
    }
    
    public String getSecond() {
        if (username.compareTo(partnerUsername) < 0) return partnerUsername;
        return username;
    }
}
