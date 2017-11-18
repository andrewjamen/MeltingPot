package utility;

import dao.UserDAO;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class Email {

    private static String G_USERNAME = "MeltingPotPenPals";  // GMail user name (just the part before "@gmail.com")
    private static String G_PASSWORD = "meltpotpass"; // GMail password
    private static String ISU_EMAIL = "";
    private static String ISU_PASSWORD = "";



    public static void sendGmail(String reciver, String subject, String body) {
        String[] to = new String[1];
        to[0] = reciver;
        
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", G_USERNAME);
        props.put("mail.smtp.password", G_PASSWORD);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(G_USERNAME));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, G_USERNAME, G_PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            String errorMessage = ae.getMessage();
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            String errorMessage = me.getMessage();
            me.printStackTrace();
        }
    }
    
    public static String sendIsuEmail(String reciver, String subject, String body) {
        
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
                return new PasswordAuthentication(ISU_EMAIL, ISU_PASSWORD); // I'm gone in two weeks anyways
            }
        });

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(ISU_EMAIL));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(reciver));

            // Set Subject: header field
            message.setSubject("Password Reset");

            // Send the actual HTML message, as big as you like
            message.setContent(body,
                    "text/html");

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            String errorMessage = mex.getMessage();
            mex.printStackTrace();
        }
        return "";
    }
    
}