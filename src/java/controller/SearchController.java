package controller;

import dao.UserDAO;
import java.util.ArrayList;
import java.util.Iterator;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.UserBean;

/**
 *
 * @author Andrew Amen
 */
@ManagedBean
@SessionScoped
public class SearchController {

    private String results;
    private UserBean theModel;
    private String searcher;
    private String quickSearchUsername;

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

    public String getQuickSearchUsername() {
        return quickSearchUsername;
    }

    public void setQuickSearchUsername(String quickSearchUsername) {
        this.quickSearchUsername = quickSearchUsername;
    }

    public ArrayList<UserBean> getResults() {

        ArrayList<UserBean> users = null;

        if (UserDAO.searchForUsers(theModel.getName(), theModel.getGender(), theModel.getAge(),
                theModel.getCity(), theModel.getState(), theModel.getReligion(), theModel.getRace(), theModel.getPolitics()) != null) {
            users = UserDAO.searchForUsers(theModel.getName(), theModel.getGender(), theModel.getAge(),
                    theModel.getCity(), theModel.getState(), theModel.getReligion(), theModel.getRace(), theModel.getPolitics());
        } else {
            return null;
        }

        //dont show your own profile
        Iterator<UserBean> iter1 = users.iterator();
        while (iter1.hasNext()) {
            String removeName = iter1.next().getUsername();
            if (searcher.equals(removeName)) {
                iter1.remove();
            }
        }

        //dont show banned accounts
        Iterator<UserBean> iter = users.iterator();
        while (iter.hasNext()) {
            boolean removedBanned = iter.next().isBanned();
            if (removedBanned) {
                iter.remove();
            }
        }

        return users;
    }

    public String quickSerch(String username) {

        quickSearchUsername = username;

        return "/Search/QuickSearch.xhtml?faces-redirect=true";
    }

    public ArrayList<UserBean> searchByUsername() {

        ArrayList<UserBean> users = null;

        if (UserDAO.searchByUsername(quickSearchUsername) != null) {
            users = UserDAO.searchByUsername(quickSearchUsername);
        } else {
            return null;
        }

        if (searcher == null) {
            searcher = "";
        }

        //dont show your own profile
        Iterator<UserBean> iter1 = users.iterator();
        while (iter1.hasNext()) {
            String removeName = iter1.next().getUsername();
            if (searcher.equals(removeName)) {
                iter1.remove();
            }
        }

        //dont show banned accounts
        Iterator<UserBean> iter = users.iterator();
        while (iter.hasNext()) {
            boolean removedBanned = iter.next().isBanned();
            if (removedBanned) {
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

    public boolean validSearch() {

        if (getResults() == null && quickSearchUsername == null) {
            return false;
        } else {
            return true;
        }
    }
}
