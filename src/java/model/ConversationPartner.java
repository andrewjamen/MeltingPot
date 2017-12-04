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
public class ConversationPartner {
    private int id;
    private UserBean user;
    private String partnerUsername;

    public ConversationPartner(UserBean user, String partnerUsername) {
        this.id = -1;
        this.user = user;
        this.partnerUsername = partnerUsername;
    }
    
    public ConversationPartner(int id, UserBean user, String partnerUsername) {
        this(user, partnerUsername);
        this.id = id;
    }
    
    @Override
    public String toString() {
        return id + " " + user.getUsername() + " " + partnerUsername;
    }

    public int getId() {
        return this.id;
    }
    
    public UserBean getUser() {
        return this.user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getPartnerUsername() {
        return partnerUsername;
    }

    public void setPartner(String partnerUsername) {
        this.partnerUsername = partnerUsername;
    }
    
    public String getFirst() {
        if (user.getUsername().compareTo(partnerUsername) < 0) return user.getUsername();
        return partnerUsername;
    }
    
    public String getSecond() {
        if (user.getUsername().compareTo(partnerUsername) < 0) return partnerUsername;
        return user.getUsername();
    }
}
