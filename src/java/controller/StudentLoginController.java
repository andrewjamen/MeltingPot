package controller;

import dao.StudentDAO;
import java.util.ArrayList;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.StudentBean;

/**
 *
 * @author Andrew Amen
 */
@ManagedBean
@SessionScoped
public class StudentLoginController {

    private boolean loggedIn = false;
    StudentBean theModel;
    String response;
    int numAttempts;

    public StudentLoginController() {
        theModel = new StudentBean();
    }

    public StudentBean getTheModel() {
        return theModel;
    }

    public void setTheModel(StudentBean theModel) {
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
            return "StudentAccount.xhtml?faces-redirect=true";
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
        StudentDAO aStudentDAO = new StudentDAO();    // Creating a new object each time.
        ArrayList<StudentBean> allUsers = aStudentDAO.findAll();

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

    public StudentBean findProfile() {

        StudentDAO aStudentDAO = new StudentDAO();
        ArrayList<StudentBean> allUsers = aStudentDAO.findByUserName(theModel.getUsername());

        theModel = allUsers.get(0);

        return theModel;
    }
}
