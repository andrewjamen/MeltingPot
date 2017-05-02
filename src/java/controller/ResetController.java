/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.StudentDAO;
import dao.UnivDAO;
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
import model.StudentBean;
import model.UnivBean;

/**
 *
 * @author IT353S712
 */
@ManagedBean
@SessionScoped
public class ResetController {
    private String username;
    private String email;
    private String univOrS;
    private String password = "";
    private String confirm = "";
    UnivBean univModel;
    StudentBean studentModel;
    
    public String reset() {
        if (univOrS.equals("University")) {
            ArrayList<UnivBean> tmp = (new UnivDAO()).findByUserName(username);
            univModel = tmp.get(0);
            univModel.setPassword(password);
            int status1 = (new UnivDAO()).updateProfile(univModel); // Doing anything with the object after this?

            if (status1 != 0) {
                return "Login.xhtml?faces-redirect=true";
            } else {
                return "error.xhtml";
            }
        }
        else if (univOrS.equals("Student")) {
            ArrayList<StudentBean> tmp = (new StudentDAO()).findByUserName(username);
            studentModel = tmp.get(0);
            studentModel.setPassword(password);
            int status1 = (new StudentDAO()).updateProfile(studentModel); // Doing anything with the object after this?

            if (status1 != 0) {
                return "Login.xhtml?faces-redirect=true";
            } else {
                return "error.xhtml";
            }
        }
        return "index.xhtml";
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
        if(univOrS.equals("University")) {
            ArrayList<UnivBean> tmp = (new UnivDAO()).findByUserName(username);
            univModel = tmp.get(0);
            email = univModel.getEmail();
        }
        else if (univOrS.equals("Student")) {
            ArrayList<StudentBean> tmp = (new StudentDAO()).findByUserName(username);
            studentModel = tmp.get(0);
            email = studentModel.getEmail();
        }

        // Sender's email ID needs to be mentioned
        String from = "cmohrfe@ilstu.edu";
        
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
                return new PasswordAuthentication("cmohrfe@ilstu.edu", "4YyhFLmBE5v3"); // I'm gone in two weeks anyways
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
            message.setContent("<a href=\"http://localhost:8080/LinkedU/faces/PasswordUpdate.xhtml?username=" + username
                             + "&univOrS=" + univOrS + "\">Click here to reset your password</a>",
                    "text/html");

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
        return "PasswordReset.xhtml";
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the univOrS
     */
    public String getUnivOrS() {
        return univOrS;
    }

    /**
     * @param univOrS the univOrS to set
     */
    public void setUnivOrS(String univOrS) {
        this.univOrS = univOrS;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the confirm
     */
    public String getConfirm() {
        return confirm;
    }

    /**
     * @param confirm the confirm to set
     */
    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }
}
