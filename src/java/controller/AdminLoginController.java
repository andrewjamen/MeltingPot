package controller;

import dao.AdminDAO;
import java.util.ArrayList;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.AdminBean;

/**
 *
 * @author Andrew Amen
 */
@ManagedBean
@SessionScoped
public class AdminLoginController {

    private boolean loggedIn = false;
    AdminBean theModel;
    String response;

    public AdminLoginController() {
        theModel = new AdminBean();
    }

    public AdminBean getTheModel() {
        return theModel;
    }

    public void setTheModel(AdminBean theModel) {
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
            FacesContext fc = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            nav.performNavigation("Login.xhtml?faces-redirect=true");
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
            return "AdminAccount.xhtml?faces-redirect=true";
        }
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession(); // the above is unnecessary once the session is invalidated
        return "index.xhtml?faces-redirect=true";

    }

    public boolean isValid() {

        boolean authenticate = false;
        AdminDAO aAdminDAO = new AdminDAO();    // Creating a new object each time.
        ArrayList<AdminBean> allUsers = aAdminDAO.findAll();

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

    public AdminBean findProfile() {
        
        AdminDAO aAdminDAO = new AdminDAO();
        ArrayList<AdminBean> allUsers = aAdminDAO.findByUserName(theModel.getUsername());
        
        theModel = allUsers.get(0);        
        
        return theModel;
    }
    
}
