package controller;

import dao.UserDAO;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.UserBean;
import dao.FriendsDAO;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.faces.application.ConfigurableNavigationHandler;
import utility.Navigation;

/**
 *
 * @author Andrew Amen
 */
@ManagedBean
@SessionScoped
public class ProfileController {

    private UserBean userModel;
    String userparam = null;

    public ProfileController() {
        userModel = new UserBean();
    }

    public UserBean getUserModel() {
        return userModel;
    }

    public void setUserModel(UserBean userModel) {
        this.userModel = userModel;
    }

    public String getURL(String username) {
        String param = "";

        try {
            param = URLEncoder.encode(username, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.err.println("Error: could not encode " + username);
        }

        return Navigation.PROFILE + param;
    }

    public void preparePage() {
        if (userparam == null) {
            preparePageFailed();
            return;
        }
        if (!findProfile(userparam)) {
            preparePageFailed();
            return;
        }
        prepareConversation(userparam);
        userparam = null;
    }

    public void preparePageFailed() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
        nav.performNavigation(Navigation.ACCOUNT);
        userparam = null;
    }

    public void prepareConversation(String username) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ConversationController conversation = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{conversationController}", ConversationController.class);
        conversation.startConversation(username);
    }

    public String addFriend(String sender) {

        String allRequests = "";

        if (!userModel.getFriendRequest().equals("")) {
            allRequests = userModel.getFriendRequest() + "\n";
        }

        allRequests += sender + " added you as a friend!";

        FriendsDAO.sendFriendRequest(userModel, allRequests);
        
        return getURL(userModel.getUsername());
    }

    public boolean isFriend(String username) {

        String isFriend = userModel.getFriendList();

        return isFriend.contains(username);
    }

    public boolean pendingRequest(String username) {

        String pendingRequest = userModel.getFriendRequest();
        UserBean profile = UserDAO.findByUsername(username);

        String otherWay = profile.getFriendRequest();

        return pendingRequest.contains(username) || otherWay.contains(userModel.getUsername());
    }

    public boolean findProfile(String username) {
        userModel = UserDAO.findByUsername(username);
        if (userModel == null) {
            return false;
        }

        return true;
    }

    public String getUserparam() {
        return userparam;
    }

    public void setUserparam(String userparam) {
        this.userparam = userparam;
    }

}
