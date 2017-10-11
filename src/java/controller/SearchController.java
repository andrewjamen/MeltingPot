package controller;

import dao.UserDAO;
import java.util.ArrayList;
import java.util.Iterator;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.UserBean;

/**
 *
 * @author jfoss
 */
@ManagedBean
@SessionScoped
public class SearchController {

    private String results;
    private UserBean theModel;
    private String searcher;

    public SearchController() {
        theModel = new UserBean();
    }

    public UserBean getTheModel() {
        return theModel;
    }

    public void setTheModel(UserBean theModel) {
        this.theModel = theModel;
    }

    public String getSearcher() {
        return searcher;
    }

    public void setSearcher(String searcher) {
        this.searcher = searcher;
    }

    public ArrayList<UserBean> getResults() {
        UserDAO userDAO = new UserDAO();
        ArrayList<UserBean> users = userDAO.searchForUsers(theModel.getName(), theModel.getGender(), theModel.getAge(),
                theModel.getCity(), theModel.getState(), theModel.getReligion(), theModel.getRace(), theModel.getPolitics());

        //dont show your own profile
        Iterator<UserBean> iter = users.iterator();
        while (iter.hasNext()) {
            String removeName = iter.next().getUsername();
            if (searcher.equals(removeName)) {
                iter.remove();
            }
        }

        return users;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public String search() {

        return "/Search/SearchResults.xhtml?faces-redirect=true";
    }
}
