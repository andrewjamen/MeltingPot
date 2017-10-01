package controller;

import dao.UserDAO;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.UserBean;

/**
 *
 * @author jfoss
 */
@ManagedBean
@SessionScoped
public class UserSearchController {

    private String results;
    private UserBean theModel;

    public UserSearchController() {
        theModel = new UserBean();
    }

    public UserBean getTheModel() {
        return theModel;
    }

    public void setTheModel(UserBean theModel) {
        this.theModel = theModel;
    }

    public ArrayList<UserBean> getResults() {
        UserDAO userDAO = new UserDAO();
        ArrayList<UserBean> users = userDAO.searchForUsers(theModel.getName(), theModel.getGender(), theModel.getAge(),
                theModel.getCity(), theModel.getState(), theModel.getReligion(), theModel.getRace(), theModel.getPolitics());

        return users;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public String search() {
        /*
        if (theModel.getName().equals("") && theModel.getGender().equals("Any") && theModel.getAge() == 0
                && theModel.getCity().equals("") && theModel.getState().equals("Any") && theModel.getReligion().equals("Any")
                && theModel.getRace().equals("Any") && theModel.getPolitics().equals("Any")) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Please provide information before searching"));
            return "UserSearch.xhtml";
        } else {

            return "UserResults.xhtml";
        }
        * 
        */
        return "UserResults.xhtml?faces-redirect=true";
    }
}
