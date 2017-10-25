package controller;

import dao.UserDAO;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.UserBean;

/**
 *
 * @author Andrew Amen
 */
@ManagedBean
@SessionScoped
public class ProfileController {

    private UserBean userModel;

    public ProfileController() {
        userModel = new UserBean();
    }

    public UserBean getUserModel() {
        return userModel;
    }
    
    public void setUserModel(UserBean userModel) {
        this.userModel = userModel;
    }

    public String getProfilePage(String username) {
        findProfile(username);
        prepareConversation(username);
        return "/Account/Profile.xhtml?faces-redirect=true";
    }
    
    public void prepareConversation(String username) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ConversationController conversation = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{conversationController}", ConversationController.class);
        conversation.startConversation(username);
    }

    public void sendMessage(String sender, String message) {

        UserDAO aUserDAO = new UserDAO();
        String allMessages = "";

        if (!userModel.getMessages().equals("")) {
            allMessages = userModel.getMessages() + "\n";
        }

        allMessages += "Message from " + sender
                + ":  \t" + message;

        aUserDAO.insertMessage(userModel, allMessages);
    }
    
    //TODO: make it so you cant add a friend twice
    public void addFriend(String sender){
    
        UserDAO aUserDAO = new UserDAO();
        String allRequests= "";

        if (!userModel.getFriendRequest().equals("")) {
            allRequests = userModel.getFriendRequest() + "\n";
        }

        allRequests += sender + " added you as a friend!";

        aUserDAO.sendFriendRequest(userModel, allRequests);
    }
    
    public boolean isFriend(String username){
        
        return userModel.getFriendList().contains(username) ;
   }

    public void findProfile(String username){
        ArrayList<UserBean> tmp = (new UserDAO()).findByUsername(username);
        userModel = tmp.get(0);
    }
}
