package controller;

import dao.UserDAO;
import java.util.ArrayList;
import javax.faces.application.ConfigurableNavigationHandler;
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
public class LoginController {

    private boolean loggedIn = false;
    UserBean theModel;
    String response;
    int numAttempts;
    boolean adminVerified = false;

    public LoginController() {
        response = "";
        theModel = new UserBean();
    }

    public UserBean getTheModel() {
        UserBean aModel = UserDAO.findByUsername(theModel.getUsername());
        
        if (aModel != null){
            theModel = aModel;
        }
        
        return theModel;
    }

    public void setTheModel(UserBean theModel) {
        this.theModel = theModel;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean isAdminVerified() {
        return adminVerified;
    }

    public void setAdminVerified(boolean adminVerified) {
        this.adminVerified = adminVerified;
    }

    public void checkIfLoggedIn() {
        if (!loggedIn) {
            FacesContext fc = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            nav.performNavigation("/Home/Home.xhtml?faces-redirect=true");
        }
    }

    public String processLogin() {
        if (theModel.getUsername().equals("admin") && theModel.getPassword().equals("123")) {
            return loginAdmin();
        } else if (!isValid()) {
            loggedIn = false;
            response = "Invalid username/password!";
            return ""; // stay right at the current page
        } else {
            this.setTheModel(findProfile());
            
            if (theModel.isBanned()) {
                loggedIn = false;
                response = "Your account has been banned for miscounduct!";
                return ""; // stay right at the current page
            }
            loggedIn = true;
            adminVerified = false;
            response = "";
            return "/Account/Account.xhtml?faces-redirect=true";
        }
    }

    public String loginAdmin() {
        adminVerified = true;
        return "/Admin/AdminAccount.xhtml?faces-redirect=true";
    }

    public String logoutAdmin() {
        adminVerified = false;
        return "/Home/Home.xhtml?faces-redirect=true";
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession(); // the above is unnecessary once the session is invalidated
        return "/Home/Home.xhtml?faces-redirect=true";

    }

    public boolean isValid() {

        boolean authenticate = false;
        UserDAO aUserDAO = new UserDAO();    // Creating a new object each time.
        ArrayList<UserBean> allUsers = aUserDAO.findAll();

        for (int i = 0; i < allUsers.size(); i++) {
            if (theModel.getUsername().equals(allUsers.get(i).getUsername())) {
                if (theModel.getPassword().equals(allUsers.get(i).getPassword())) {
                    authenticate = true;
                    break;
                }
            }
        }

        return authenticate;
    }

    private UserBean findProfile() {

        theModel = UserDAO.findByUsername(theModel.getUsername());

        return theModel;
    }

}
