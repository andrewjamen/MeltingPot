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
import model.Conversations;
import model.Message;
import model.UserBean;
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

    private Conversation conversationModel;
    private Conversations conversations;
    private UserBean user;
    private String content;

    /**
     * Creates a new instance of ConversationController
     */
    public ConversationController() {
        this.conversationModel = null;
        this.conversations = null;
        this.user = null;
        this.content = "";
    }

    /**
     * Initializes the conversation model.
     *
     * @param partnerUsername
     */
    public void startConversation(String partnerUsername) {
        if (user == null) {
            init();
        }

        content = "";
        conversationModel = new Conversation(this.user.getUsername(), partnerUsername);
    }

    public void prepareConversations() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        LoginController lc = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{loginController}", LoginController.class);
        if (!lc.checkIfLoggedIn()) {
            return;
        }
        this.updateConversations();
    }

    /**
     * Initializes the username.
     */
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        this.user = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{loginController}", LoginController.class).getTheModel();
    }

    public void updateConversations() {
        if (conversations == null) {
            if (user == null) {
                init();
            }
            conversations = new Conversations(user);
        } else {
            conversations.update();
        }
    }

    public void deleteListedConversation(int index) {
        conversations.deleteConversation(index);
    }

    public int conversationCount() {
        this.updateConversations();
        return conversations.getCount();
    }

    public void sendMessage() {
        if (content.equals("")) {
            return;
        }
        if (conversationModel == null) {
            System.err.println("Error: cannot send message (\"" + content + "\"). Conversation is null.");
            return;
        }
        conversationModel.sendMessage(new Message(user.getUsername(), conversationModel.getPartnerUsername(), this.content, new Date()));
        this.content = "";
    }

    public void receiveMessages() {
        if (conversationModel == null) {
            System.err.println("Error: cannot receive messages. Conversation is null.");
            return;
        }
        if (conversationModel.receiveMessages()) {
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
