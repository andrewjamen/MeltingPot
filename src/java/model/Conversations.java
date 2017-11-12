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
    private ArrayList<ConversationPair> conversationsList = null;
   
    public Conversations(String username) {
        this.update(username);
    }

    public final void update(String username) {
        conversationsList = ConversationDAO.getAllConversationsByUsername(username);
    }
    
    public void deleteConversation(int index) {
          ConversationDAO.deleteConversationByID(conversationsList.get(index).getId());
          conversationsList.remove(index);
    }
    
    public int getCount() {
        if (conversationsList == null) return 0;
        return conversationsList.size();
    }
    
    public ArrayList<ConversationPair> getConversationsList() {
        return conversationsList;
    }
    
    public boolean noConversations() {
        return conversationsList.isEmpty();
    }
}
