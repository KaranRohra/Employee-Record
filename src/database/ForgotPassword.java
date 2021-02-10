package database;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class ForgotPassword {
    public static void sendMail(String recipient,int otp) {
        System.out.println("Preparing send mail");
        Properties properties = new Properties();

        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");

        String email = "rohrakaran38@gmail.com";
        String password = "KARANrohra@";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email,password);
            }
        });

        MimeMessage message = prepareMessage(session, email, recipient, otp);

        try {
            assert message != null;
            Transport.send(message);
            System.out.println("Message sent successfully");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private static MimeMessage prepareMessage(Session session, String email, String recipient,int otp) {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("Employee Record OTP");
            message.setText("OTP: ".concat(String.valueOf(otp)));
            return message;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
