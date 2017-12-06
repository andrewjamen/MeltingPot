package utility;

import java.io.IOException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class SendEmail {

    private static String G_USERNAME = "MeltingPotPenPals@gmail.com";  // GMail user name (just the part before "@gmail.com")
    private static String G_PASSWORD = "meltpotpass"; // GMail password

    public static void sendGmail(String reciver, String subject, String body) {

        Properties props = System.getProperties();

        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        //props.put("mail.smtp.email", G_USERNAME);
        //props.put("mail.smtp.password", G_PASSWORD);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(G_USERNAME, G_PASSWORD);
            }
        });
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(G_USERNAME));

            message.setRecipient(Message.RecipientType.TO,
                    new InternetAddress(reciver));

            message.setSubject(subject);
            message.setText(body);
            //Transport transport = session.getTransport();

            Transport.send(message);
            //transport.close();
        } catch (Exception me) {
            String errorMessage = me.getMessage();
            me.printStackTrace();
        }
    }

    
    //TODO: fix local host if published
    public static void passResetEmail(String reciever, String email) {

        String body = "<a href=\"http://localhost:8080/MeltingPot/faces" + Navigation.PASSWORD_UPDATE + "?username=" + reciever + "\">Click here to reset your password</a>";

        sendGmail(email, "Password Reset", body);
    }

    public static void main(String args[]) {
        SendEmail e = new SendEmail();
        e.sendGmail("MeltingPotPenPals@gmail.com", "test", "tester email");
    }
}
