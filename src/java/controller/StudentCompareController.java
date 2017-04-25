package controller;

import dao.StudentDAO;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.StudentBean;

/**
 *
 * @author jfoss
 */
@ManagedBean
@SessionScoped
public class StudentCompareController {
    private StudentBean post1;
    private StudentBean post2;
    
    public StudentCompareController() {
        post1 = new StudentBean();
        post2 = new StudentBean();
    }

    public StudentBean getPost1() {
        return post1;
    }
    
    public StudentBean getPost2() {
        return post2;
    }

    public void setPost1(StudentBean post1) {
        this.post1 = post1;
    }
    
    public void setPost2(StudentBean post2) {
        this.post2 = post2;
    }

    public ArrayList<StudentBean> getPost2Results() {
        StudentDAO studentDAO = new StudentDAO();
        ArrayList<StudentBean> post2Users = studentDAO.searchForUsersExcept(post1.getUsername(), post2.getFirstName(), post2.getLastName(), post2.getAct(), post2.getGpa());
        
        return post2Users;
    }
    
    public String compare1(String username) {
        ArrayList<StudentBean> tmp = (new StudentDAO()).findByUserName(username);
        post1 = tmp.get(0);
        return "StudentCompareSearch.xhtml?faces-redirect=true";
    }
    
    public String compare2(String username) {
        ArrayList<StudentBean> tmp = (new StudentDAO()).findByUserName(username);
        post2 = tmp.get(0);
        return "StudentCompare.xhtml?faces-redirect=true";
    }
}
