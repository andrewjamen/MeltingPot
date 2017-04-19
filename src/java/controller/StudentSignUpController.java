package controller;

import dao.StudentDAO;
import java.util.ArrayList;
import java.util.Properties;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import model.StudentBean;

/**
 * @author Andrew Amen
 */
@ManagedBean
@SessionScoped
public class StudentSignUpController {

    private String response;
    private StudentBean studentBean;

    public StudentSignUpController() {
        studentBean = new StudentBean();
    }

    public StudentBean getStudentBean() {
        return studentBean;
    }

    public void setStudentBean(StudentBean studentBean) {
        this.studentBean = studentBean;
    }

    public String getResponse() {
        String resultStr = "";
        resultStr += "Hello, " + studentBean.getFirstName() + " " + studentBean.getLastName() + "<br/>";
        resultStr += "You have successfully created an account with the following information:" + "<br/>" + "<br/>";
        resultStr += "User Name: " + studentBean.getUsername() + "<br/>";
        resultStr += "Email: " + studentBean.getEmail() + "<br/>" + "<br/>";
        resultStr += "Address: " + studentBean.getAddress() + "<br/>";
        resultStr += "City: " + studentBean.getCity() + "<br/>";
        resultStr += "State: " + studentBean.getState() + "<br/>" + "<br/>";
        resultStr += "High School: " + studentBean.getHighSchool() + "<br/>";
        resultStr += "ACT: " + studentBean.getAct() + "<br/>";
        resultStr += "GPA: " + studentBean.getGpa() + "<br/>";
        resultStr += "Extra Curricular Activities: " + "<br/>" + studentBean.getExtra() + "<br/>" + "<br/>";
        resultStr += "Desired Major: " + studentBean.getMajor() + "<br/>" + "<br/>";
        resultStr += "Personal Statement: " + "<br/>" + studentBean.getStatement() + "<br/>"+"<br/>";

        resultStr += "Thank you for your registration!" + "<br/>" + "<br/>";
        response = resultStr;

        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
    
    //TODO: change from account confimation to appointment confimation
    public void sendEmail() {
        // Recipient's email ID needs to be mentioned.
        String to = studentBean.getEmail();

        // Sender's email ID needs to be mentioned
        String from = "ajamen@ilstu.edu";

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
            message.setSubject("Amen Snowboarads - New account");

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

        if ("".equals(studentBean.getUsername()) || studentBean.getUsername() == null) {
            return "Enter a username!";
        }
        boolean goodLogin = true;
        StudentDAO aSignUpDAO = new StudentDAO();
        ArrayList<StudentBean> allUsers = aSignUpDAO.findAll();

        for (int i = 0; i < allUsers.size(); i++) {
            if (studentBean.getUsername().equals(allUsers.get(i).getUsername())) {
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

        if ("".equals(studentBean.getConfirm()) || studentBean.getConfirm() == null) {
            return "Enter a password!";
        }
        boolean goodPassword = false;

        if (studentBean.getPassword().equals(studentBean.getConfirm())){
            goodPassword = true;
        }

        if (goodPassword == false) {
            return "Passwords do not match!";
        } else {
            return "Passwords match!";
        }
    }    

    public String createProfile() {
     
                
        if (!studentBean.getPassword().equals(studentBean.getConfirm())) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Passwords do not match! Try again"));
            return "StudentSignUp.xhtml";
        }

        StudentDAO aSignUpDAO = new StudentDAO();
        ArrayList<StudentBean> users = aSignUpDAO.findByUserName(studentBean.getUsername());

        if (users.size() > 0) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Username Already Exists!"));
            return "StudentSignUp.xhtml";
        }

        int rowCount = aSignUpDAO.createProfile(studentBean); 
        if (rowCount == 1) {
            return "StudentSuccess.xhtml";
        } else {
            return "error.xhml";
        }
    }

}
