package controller;

import dao.UserDAO;
import java.util.ArrayList;
import java.util.Properties;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import model.UserBean;

/**
 * @author Andrew Amen
 */
@ManagedBean
@SessionScoped
public class SignUpController {

    private String response;
    private String status = "";
    private UserBean userBean;

    public SignUpController() {
        userBean = new UserBean();
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public String getResponse() {

        String resultStr = "";
        resultStr += "Hello, " + userBean.getName() + "<br/>";
        resultStr += "You have successfully created an account with the following information:" + "<br/>" + "<br/>";
        resultStr += "User Name: " + userBean.getUsername() + "<br/>";
        resultStr += "Email: " + userBean.getEmail() + "<br/>" + "<br/>";
        resultStr += "City: " + userBean.getCity() + "<br/>";
        resultStr += "State: " + userBean.getState() + "<br/>" + "<br/>";
        resultStr += "Age: " + userBean.getAge() + "<br/>";
        resultStr += "Gender: " + userBean.getGender() + "<br/>";
        resultStr += "Religion: " + userBean.getReligion() + "<br/>";
        resultStr += "Race: " + userBean.getRace() + "<br/>";
        resultStr += "Political Views: " + userBean.getPolitics() + "<br/>" + "<br/>";
        resultStr += "Biography: " + "<br/>" + userBean.getBio() + "<br/>" + "<br/>";

        resultStr += "Thank you for your registration!" + "<br/>" + "<br/>";
        response = resultStr;

        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //TODO: change from account confimation to appointment confimation
    public void sendEmail() {
        // Recipient's email ID needs to be mentioned.
        String to = userBean.getEmail();

        // Sender's email ID needs to be mentioned
        String from = "akolet@ilstu.edu";

        // Assuming you are sending email from this host
        String host = "smtp.ilstu.edu";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.user", "yourID"); // if needed
        properties.setProperty("mail.password", "yourPassword"); // if needed

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Melting Pot - New account");

            // Send the actual HTML message, as big as you like
            message.setContent(getResponse(),
                    "text/html");

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public String checkAvailable() {

        if ("".equals(userBean.getUsername()) || userBean.getUsername() == null) {
            return "Enter a username!";
        }
        boolean goodLogin = true;
        
        ArrayList<UserBean> allUsers = UserDAO.findAll();

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

    public String createProfile() {
        status = "";

        UserBean match = UserDAO.findByUsername(userBean.getUsername());

        if (userBean.getUsername().equals("") || userBean.getUsername() == null) {
            status = "Enter a Username!";
            return "";
        }
        if (match != null) {
            status = "Username Already Exists!";
            return "";
        }
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

        int rowCount = UserDAO.createProfile(userBean);
        if (rowCount == 1) {
            return "/Account/SignUpSuccess.xhtml?faces-redirect=true";
        } else {
            return "/Home/Error.xhtml?faces-redirect=true";
        }
    }

}
