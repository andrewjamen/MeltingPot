/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.Conversation;
import model.Conversations;
import model.Message;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Perry Kaufman
 */
@ManagedBean
@Named(value = "conversationController")
@SessionScoped
public class ConversationController {

    private static final String MESSAGE_BOARD_ID = "message_form:message_board";
    private static final String SCROLL_FUNCTION = "scrollToBottom()";
    
    private Conversation conversationModel = null;
    private Conversations conversations = null;
    private String username = null;
    private String content = "";
    
    /**
     * Creates a new instance of ConversationController
     */
    public ConversationController() {
        if (conversationModel == null) conversationModel = new Conversation();
        if (conversations == null) conversations = new Conversations(username);
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
    
    public void prepareConversations() {
        //TODO: If not logged in, redirect to home.
        this.updateConversations();
    }
    
    public void updateConversations() {
        if (conversations == null) conversations = new Conversations(username);
        else conversations.update(username);
    }
    
    public void deleteListedConversation(int index) {
        conversations.deleteConversation(index);
    }
    
    public int conversationCount() {
        this.updateConversations();
        return conversations.getCount();
    }
    
    public void sendMessage() {
        if (content.equals("")) return;
        conversationModel.sendMessage(new Message(username, conversationModel.getPartnerUsername(), this.content, new Date()));
        this.content="";
    }
    
    public void receiveMessages() {
        if(conversationModel.receiveMessages()) {
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.update(MESSAGE_BOARD_ID);
            requestContext.execute(SCROLL_FUNCTION);
        }
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

    public Conversations getConversations() {
        return conversations;
    }

    public void setConversations(Conversations conversations) {
        this.conversations = conversations;
    }    
}
