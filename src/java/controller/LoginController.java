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

    public LoginController() {
        theModel = new UserBean();
    }

    public UserBean getTheModel() {
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

    public void checkIfLoggedIn() {
        if (!loggedIn) {
            // Can't just return "login" as it not an "action" event (// Ref: http://stackoverflow.com/questions/16106418/how-to-perform-navigation-in-prerenderview-listener-method)
            FacesContext fc = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            nav.performNavigation("Login?faces-redirect=true");
        }
    }

    public String processLogin() {
        if (!isValid()) {
            loggedIn = false;
            response = "Invalid username/password!";
            return ""; // stay right at the current page
        } else {
            this.setTheModel(findProfile());
            loggedIn = true;
            response = "";
            return "/Account/Account.xhtml?faces-redirect=true";
        }
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
