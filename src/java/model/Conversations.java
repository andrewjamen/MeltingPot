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
public class Conversations {
    private ArrayList<ConversationPartner> conversationsList = null;
    private UserBean user;
   
    public Conversations(UserBean user) {
        this.user = user;
        this.update();
    }

    public final void update() {
        if (this.user == null) return;
        conversationsList = ConversationDAO.getAllConversationsByUsername(this.user);
    }
    
    public void deleteConversation(int index) {
          ConversationDAO.deleteConversationByID(conversationsList.get(index).getId());
          conversationsList.remove(index);
    }
    
    public int getCount() {
        if (conversationsList == null) return 0;
        return conversationsList.size();
    }
    
    public ArrayList<ConversationPartner> getConversationsList() {
        return conversationsList;
    }
    
    public boolean noConversations() {
        return conversationsList.isEmpty();
    }
    
    public UserBean getUser() {
        return this.user;
    }
}
