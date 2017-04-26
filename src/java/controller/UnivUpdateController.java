/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UnivDAO;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.UnivBean;

/**
 *
 * @author Andrew
 */
@ManagedBean
@SessionScoped
public class UnivUpdateController {

    private UnivBean theModel;
    private String updateStatus;

    public UnivUpdateController() {
        theModel = new UnivBean();
    }

    public UnivBean getTheModel() {
        return theModel;
    }

    public void setTheModel(UnivBean theModel) {
        this.theModel = theModel;
    }

    public String getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(String updateStatus) {
        this.updateStatus = updateStatus;
    }

    public String retrieveProfile(UnivBean theBean) {
        theModel = theBean;
        UnivDAO aUnivDAO = new UnivDAO();    // Creating a new object each time.
        ArrayList users = aUnivDAO.findByUserName(theModel.getUsername()); // Doing anything with the object after this?
        theModel = (UnivBean) users.get(0); // if multiple found, just pick the 1st one. If none?
        if (theModel != null) {
            return "UnivUpdate.xhtml?faces-redirect=true"; // navigate to "update2.xhtml"
        } else {
            return "error.xhtml";
        }
    }

    //TODO: dont log out
    public String updateUser() {
        UnivDAO aUnivDAO = new UnivDAO();   // Creating a new object each time.
        int status1 = aUnivDAO.updateProfile(theModel); // Doing anything with the object after this?
        if (status1 != 0) {
            updateStatus = "User Record updated successfully ...";
        } else {
            updateStatus = "User Record update failed!";
        }

        if (status1 != 0) {
            UnivLoginController login = new UnivLoginController();
            login.logout();
            return "Login.xhtml?faces-redirect=true";
        } else {
            return "error.xhtml";
        }

    }

    public String checkAvailable() {

        if ("".equals(theModel.getUsername()) || theModel.getUsername() == null) {
            return "Enter a username!";
        }
        boolean goodLogin = true;
        UnivDAO aUnivDAO = new UnivDAO();
        ArrayList<UnivBean> allUsers = aUnivDAO.findAll();

        for (int i = 0; i < allUsers.size(); i++) {
            if (theModel.getUsername().equals(allUsers.get(i).getUsername())) {
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

        if ("".equals(theModel.getConfirm()) || theModel.getConfirm() == null) {
            return "Enter a password!";
        }
        boolean goodPassword = false;

        if (theModel.getPassword().equals(theModel.getConfirm())) {
            goodPassword = true;
        }

        if (goodPassword == false) {
            return "Passwords do not match!";
        } else {
            return "Passwords match!";
        }
    }

}
