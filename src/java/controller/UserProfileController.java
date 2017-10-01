/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UserDAO;
import dao.AdminDAO;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.UserBean;
import model.AdminBean;

/**
 *
 * @author Andrew Amen
 */
@ManagedBean
@SessionScoped
public class UserProfileController {

    private UserBean userModel;
    String requestMessage = "";
    Date date;

    public UserProfileController() {
        userModel = new UserBean();
    }

    public UserBean getUserModel() {
        return userModel;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    public void setUserModel(UserBean userModel) {
        this.userModel = userModel;
    }

    public String getProfilePage(String username) {
        ArrayList<UserBean> tmp = (new UserDAO()).findByUsername(username);
        userModel = tmp.get(0);
        return "UserProfile.xhtml?faces-redirect=true";
    }

    public void sendMessage(String sender, String message) {

        UserDAO aUserDAO = new UserDAO();

        findProfile(userModel.getUsername());
        if (!userModel.getMessages().equals("")) {
            requestMessage = userModel.getMessages() + "\n";
        }

        requestMessage += "Message from " + sender
                + ":  \t" + message;

        aUserDAO.insertMessage(userModel, requestMessage);
    }

    public void findProfile(String username){
        ArrayList<UserBean> tmp = (new UserDAO()).findByUsername(username);
        userModel = tmp.get(0);
    }
}
