package controller;

import dao.UnivDAO;
import java.util.ArrayList;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.UnivBean;

/**
 *
 * @author Andrew Amen
 */
@ManagedBean
@SessionScoped
public class UnivLoginController {

    private boolean loggedIn = false;
    UnivBean theModel;
    String response;

    public UnivLoginController() {
        theModel = new UnivBean();
    }

    public UnivBean getTheModel() {
        return theModel;
    }

    public void setTheModel(UnivBean theModel) {
        this.theModel = theModel;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    /**
     * Get the value of loggedIn
     *
     * @return the value of loggedIn
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * Set the value of loggedIn
     *
     * @param loggedIn new value of loggedIn
     */
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
            UnivAccountController theUnivAccountController = new UnivAccountController(findProfile());
            theUnivAccountController.setUnivModel(findProfile());
            loggedIn = true;
            response = "";
            return "UnivAccount.xhtml?faces-redirect=true";
        }
    }

    public String logout() {
//        loggedIn = false;
//        theModel.setUsername("");
//        theModel.setPassword("");
//        theModel.setFavPL("");
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession(); // the above is unnecessary once the session is invalidated
        return "index.xhtml?faces-redirect=true";

    }

    public boolean isValid() {

        boolean authenticate = false;
        UnivDAO aUnivDAO = new UnivDAO();    // Creating a new object each time.
        ArrayList<UnivBean> allUsers = aUnivDAO.findAll();

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

    public UnivBean findProfile() {
        
        UnivDAO aUnivDAO = new UnivDAO();
        ArrayList<UnivBean> allUsers = aUnivDAO.findByUserName(theModel.getUsername());
        
        theModel = allUsers.get(0);        
        
        return theModel;
    }

}
