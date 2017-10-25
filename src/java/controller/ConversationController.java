/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.Date;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.context.FacesContext;
import model.Conversation;
import model.Message;

/**
 *
 * @author Perry
 */
@Named(value = "conversationController")
@Dependent
public class ConversationController {

    private Conversation conversationModel = null;
    private String username = null;
    
    /**
     * Creates a new instance of ConversationController
     */
    public ConversationController() {
        if (conversationModel == null) conversationModel = new Conversation();
        if (username == null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            LoginController login = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{loginController}", LoginController.class);
            this.username = login.getTheModel().getUsername();
        }
    }
    
    public void startConversation(String partnerUsername) {
        conversationModel = new Conversation(this.username, partnerUsername);
    }

    public void sendMessage(String content) {
        conversationModel.sendMessage(new Message(username, conversationModel.getPartnerUsername(), content, new Date()));
    }
    
    public void receiveMessages() {
        conversationModel.receiveMessages();
    }
    
    public Conversation getConversationModel() {
        return conversationModel;
    }

    public void setConversationModel(Conversation conversation) {
        this.conversationModel = conversation;
    }
    
    
    
}
