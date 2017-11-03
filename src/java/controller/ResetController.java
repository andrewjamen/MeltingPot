/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UserDAO;
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

/**
 *
 * @author IT353S712
 */
@ManagedBean
@SessionScoped
public class ResetController {
    private String username;
    private String email;
    private String userBean;
    private String password = "";
    private String confirm = "";
    AdminBean univModel;
    UserBean userModel;
    
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

        if (password.equals(confirm)){
            goodPassword = true;
        }

        if (goodPassword == false) {
            return "Passwords do not match!";
        } else {
            return "Passwords match!";
        }
    }
    
    public String sendReset() {
        
            userModel = UserDAO.findByUsername(username);
            email = userModel.getEmail();
 

        // Sender's email ID needs to be mentioned
        String from = "akolet@ilstu.edu";
        
        // Assuming you are sending email from this host
        String host = "outlook.office365.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.port", "587");
        // Get the default Session object.
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("akolet@ilstu.edu", "4YyhFLmBE5v3"); // I'm gone in two weeks anyways
            }
        });

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(email));

            // Set Subject: header field
            message.setSubject("Password Reset");

            // Send the actual HTML message, as big as you like
            message.setContent("<a href=\"http://gfish2.it.ilstu.edu/MeltingPot/faces/Password/PasswordUpdate.xhtml?username=" + username
                             + "&univOrS=" + userBean + "\">Click here to reset your password</a>",
                    "text/html");

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
        return "/Password/PasswordReset.xhtml?faces-redirect=true";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserBean() {
        return userBean;
    }


    public void setUserBean(String userBean) {
        this.userBean = userBean;
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
}
