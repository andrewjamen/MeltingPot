package controller;

import dao.UnivDAO;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.UnivBean;

/**
 *
 * @author Andrew
 */
@ManagedBean
@SessionScoped
public class UnivSignUpController {
    
    private String response;
    private UnivBean univBean;

    public UnivSignUpController() {
        univBean = new UnivBean();
    }

    public UnivBean getUnivBean() {
        return univBean;
    }

    public void setUnivBean(UnivBean univBean) {
        this.univBean = univBean;
    }

    public String getResponse() {
        String resultStr = "";
        resultStr += "Hello, " + univBean.getName() + "<br/>";
        resultStr += "You have successfully created an account with the following information:" + "<br/>" + "<br/>";
        resultStr += "User Name: " + univBean.getUserName() + "<br/>";
        resultStr += "Email: " + univBean.getEmail() + "<br/>" + "<br/>";
        resultStr += "Address: " + univBean.getAddress() + "<br/>";
        resultStr += "City: " + univBean.getCity() + "<br/>";
        resultStr += "State: " + univBean.getState() + "<br/>" + "<br/>";
        resultStr += "Average ACT: " + univBean.getAvgAct() + "<br/>";
        resultStr += "Average GPA: " + univBean.getAvgGpa() + "<br/>" + "<br/>";
      
        resultStr += "Thank you for your registration!" + "<br/>" + "<br/>";
        response = resultStr;

        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
    
    public String checkAvailable() {

        if ("".equals(univBean.getUserName()) || univBean.getUserName() == null) {
            return "Enter a username!";
        }
        boolean goodLogin = true;
        UnivDAO aUnivDAO = new UnivDAO();
        ArrayList<UnivBean> allUsers = aUnivDAO.findAll();

        for (int i = 0; i < allUsers.size(); i++) {
            if (univBean.getUserName().equals(allUsers.get(i).getUserName())) {
                goodLogin = false;
            }
        }

        if (goodLogin == false) {
            return "Username already taken!";
        } else {
            return "Username available!";
        }
    }
    
    public String checkMatch() {

        if ("".equals(univBean.getConfirm()) || univBean.getConfirm() == null) {
            return "Enter a password!";
        }
        boolean goodPassword = false;

        if (univBean.getPassword().equals(univBean.getConfirm())){
            goodPassword = true;
        }

        if (goodPassword == false) {
            return "Passwords do not match!";
        } else {
            return "Passwords match!";
        }
    }    

    public String createProfile() {
                
        if (!univBean.getPassword().equals(univBean.getConfirm())) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Passwords do not match! Try again"));
            return "UnivSignUp.xhtml";
        }

        UnivDAO aUnivDAO = new UnivDAO();
        ArrayList<UnivBean> users = aUnivDAO.findByUserName(univBean.getUserName());

        if (users.size() > 0) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Username Already Exists!"));
            return "UnivSignUp.xhtml";
        }

        int rowCount = aUnivDAO.createProfile(univBean);
        if (rowCount == 1) {
            return "UnivSuccess.xhtml";
        } else {
            return "error.xhml";
        }
    }

}