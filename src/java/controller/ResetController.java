/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UserDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import model.UserBean;
import model.AdminBean;
import utility.SendEmail;

/**
 *
 * @author IT353S712
 */
@ManagedBean
@SessionScoped
public class ResetController {

    private String username;
    private String email;
    private UserBean userModel;
    private String password = "";
    private String confirm = "";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserBean getUserModel() {
        return userModel;
    }

    public void setUserModel(UserBean userModel) {
        this.userModel = userModel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String reset() {

        userModel = UserDAO.findByUsername(username);
        userModel.setPassword(password);
        int status1 = (new UserDAO()).updateProfile(userModel); // Doing anything with the object after this?

        if (status1 != 0) {
            return "/Account/Login.xhtml?faces-redirect=true";
        } else {
            return "/Home/Error.xhtml?faces-redirect=true";
        }

    }

    public String checkMatch() {

        if (confirm.equals("") || confirm == null) {
            return "Enter a password!";
        }
        boolean goodPassword = false;

        if (password.equals(confirm)) {
            goodPassword = true;
        }

        if (goodPassword == false) {
            return "Passwords do not match!";
        } else {
            return "Passwords match!";
        }
    }

    public String sendReset() throws IOException {
        
        userModel = UserDAO.findByUsername(username);
        

        SendEmail.passResetEmail(username, userModel.getEmail());

        return "/Password/PasswordReset.xhtml?faces-redirect=true";
    }
}
