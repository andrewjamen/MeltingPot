package controller;

import dao.AdminDAO;
import dao.UserDAO;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.UserBean;
import dao.FriendsDAO;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.faces.application.ConfigurableNavigationHandler;
import model.Report;
import model.Status;
import utility.Navigation;

/**
 *
 * @author Andrew Amen
 */
@ManagedBean
@SessionScoped
public class ProfileController {

    private UserBean userModel;
    private Report report;
    String userparam = null;
    private Status statusModel;

    public ProfileController() {
        userModel = new UserBean();
        statusModel = null;
    }

    public UserBean getUserModel() {
        return userModel;
    }

    public void setUserModel(UserBean userModel) {
        this.userModel = userModel;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
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
        this.userModel = UserDAO.findByUsername(username);
        if (this.userModel == null) {
            return false;
        }
        this.statusModel = new Status(this.userModel);
        return true;
    }

    public String getUserparam() {
        return userparam;
    }

    public void setUserparam(String userparam) {
        this.userparam = userparam;
    }

    public String submitReport(String username, String message) {

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        Date current = Calendar.getInstance().getTime();
        String reportTime = df.format(current);

        int reportID = AdminDAO.newReportID();

        report = new Report(reportID, username, userModel.getUsername(), message, reportTime);
        
        AdminDAO.addReport(report);        

        return Navigation.ACCOUNT;
    }
    
    public String getCurrentStatus() {
        if (this.statusModel == null) return "N/A";
        if (statusModel.getCurrentStatus() == null || statusModel.getCurrentStatus().equals("")) return "N/A";
        return this.statusModel.getCurrentStatus();
    }

}
