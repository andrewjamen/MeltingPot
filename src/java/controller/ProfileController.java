package controller;

import dao.UserDAO;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.UserBean;
import dao.FriendsDAO;

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
    
    
    //TODO: make it so you cant add a friend twice
    public void addFriend(String sender){
    
        String allRequests= "";

        if (!userModel.getFriendRequest().equals("")) {
            allRequests = userModel.getFriendRequest() + "\n";
        }

        allRequests += sender + " added you as a friend!";

        FriendsDAO.sendFriendRequest(userModel, allRequests);
    }
    
    public boolean isFriend(String username){
        
        String isFriend = userModel.getFriendList();
        
        return isFriend.contains(username);
   }
    
    public boolean pendingRequest(String username){
        
        
        String pendingRequest = userModel.getFriendRequest();   
        UserBean profile = UserDAO.findByUsername(username);
        
        String otherWay = profile.getFriendRequest();
        
        
        return pendingRequest.contains(username) || otherWay.contains(userModel.getUsername());
    }

    public void findProfile(String username){
        userModel = UserDAO.findByUsername(username);
    }
}
