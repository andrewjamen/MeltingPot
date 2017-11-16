/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UserDAO;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.UserBean;
import utility.Navigation;

/**
 *
 * @author Andrew
 */
@ManagedBean
@SessionScoped
public class UpdateController {

    private UserBean userBean;
    private String updateStatus;
    private String status;

    public UpdateController() {
        userBean = null;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public String getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(String updateStatus) {
        this.updateStatus = updateStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getURL() {
        return Navigation.UPDATE_PROFILE;
    }

    public void preparePage() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        LoginController lc = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{loginController}", LoginController.class);
        if (!lc.checkIfLoggedIn()) {
            return;
        }

        if (userBean == null) {
            userBean = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{loginController}", LoginController.class).getTheModel();
        }

        userBean = UserDAO.findByUsername(userBean.getUsername()); //This could cause problems. Shouldn't be creating a new userbean.
        
        if (userBean == null) {
            //Nothing should need to be here...
        }
    }

    //TODO: dont log out
    public String updateUser() {
        UserDAO aStudentDAO = new UserDAO();

        if (userBean.getName().equals("") || userBean.getName() == null) {
            status = "Enter a Name!";
            return "";
        }
        if (!userBean.getPassword().equals(userBean.getConfirm())) {
            status = "Passwords Do Not Match!";
            return "";
        }
        if (userBean.getPassword().equals("") || userBean.getPassword() == null) {
            status = "Enter a Password!";
            return "";
        }
        if (userBean.getEmail().equals("") || userBean.getEmail() == null) {
            status = "Enter an Email!";
            return "";
        }
        if (userBean.getCity().equals("") || userBean.getCity() == null) {
            status = "Enter a City!";
            return "";
        }
        if (userBean.getAge() == 0 || userBean.getEmail() == null) {
            status = "Enter your Age!";
            return "";
        }

        int status1 = aStudentDAO.updateProfile(userBean);
        if (status1 != 0) {
            updateStatus = "User Record updated successfully!";
        } else {
            updateStatus = "User Record update failed!";
        }

        if (status1 != 0) {
            LoginController login = new LoginController();
            login.logout();
            return "/Account/Login.xhtml?faces-redirect=true";
        } else {
            return "/Home/Error.xhtml?faces-redirect=true";
        }

    }

    public String checkAvailable() {

        if ("".equals(userBean.getUsername()) || userBean.getUsername() == null) {
            return "Enter a username!";
        }
        boolean goodLogin = true;
        UserDAO aStudentDAO = new UserDAO();
        ArrayList<UserBean> allUsers = aStudentDAO.findAll();

        for (int i = 0; i < allUsers.size(); i++) {
            if (userBean.getUsername().equals(allUsers.get(i).getUsername())) {
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

        if ("".equals(userBean.getConfirm()) || userBean.getConfirm() == null) {
            return "Enter a password!";
        }
        boolean goodPassword = false;

        if (userBean.getPassword().equals(userBean.getConfirm())) {
            goodPassword = true;
        }

        if (goodPassword == false) {
            return "Passwords do not match!";
        } else {
            return "Passwords match!";
        }
    }

}
