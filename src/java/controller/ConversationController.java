/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.Conversation;
import model.Message;

/**
 *
 * @author Perry Kaufman
 */
@ManagedBean
@Named(value = "conversationController")
@SessionScoped
public class ConversationController {

    private Conversation conversationModel = null;
    private String username = null;
    private String content = "";
    
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
    
    /**
     * Initializes the conversation model.
     * @param partnerUsername 
     */
    public void startConversation(String partnerUsername) {
        content="";
        conversationModel = new Conversation(this.username, partnerUsername);
    }

    public void sendMessage() {
        if (content.equals("")) return;
        conversationModel.sendMessage(new Message(username, conversationModel.getPartnerUsername(), this.content, new Date()));
        this.content="";
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    
    
}
