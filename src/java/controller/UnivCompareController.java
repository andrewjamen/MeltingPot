package controller;

import dao.UnivDAO;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.UnivBean;

/**
 *
 * @author jfoss
 */
@ManagedBean
@SessionScoped
public class UnivCompareController {
    private UnivBean post1;
    private UnivBean post2;
    
    public UnivCompareController() {
        post1 = new UnivBean();
        post2 = new UnivBean();
    }

    public UnivBean getPost1() {
        return post1;
    }
    
    public UnivBean getPost2() {
        return post2;
    }

    public void setPost1(UnivBean post1) {
        this.post1 = post1;
    }
    
    public void setPost2(UnivBean post2) {
        this.post2 = post2;
    }

    public ArrayList<UnivBean> getPost2Results() {
        UnivDAO univDAO = new UnivDAO();
        ArrayList<UnivBean> post2Users = univDAO.searchForUsersExcept(post1.getUsername(), post2.getName(), post2.getState(), post2.getAvgAct(), post2.getAvgGpa());
        
        return post2Users;
    }
    
    public String compare1(String username) {
        ArrayList<UnivBean> tmp = (new UnivDAO()).findByUserName(username);
        post1 = tmp.get(0);
        return "UnivCompareSearch.xhtml?faces-redirect=true";
    }
    
    public String compare2(String username) {
        ArrayList<UnivBean> tmp = (new UnivDAO()).findByUserName(username);
        post2 = tmp.get(0);
        return "UnivCompare.xhtml?faces-redirect=true";
    }
}
