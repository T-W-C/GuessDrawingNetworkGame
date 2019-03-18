package email;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

public class EmailHandler {

    private int activationCode;

    public int getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(int activationCode) {
        this.activationCode = activationCode;
    }

    /**
     * This method will connect to the Google's SMTP server and allow the application to send emails
     * @param targetEmail Target email to send email to, this is an array, so you can a email to multiple addresses
     * @param subject The subject title of the email
     * @param body Body of the email
     */
    public void SendEmail(String[] targetEmail, String subject, String body) {

        Properties emailProperties = System.getProperties();

        /* Credentials for Email server */
        String emailHost = "smtp.gmail.com";
        String emailUsername = "group1swproject";
        String emailPassword = "Lol123!$!";

        /* Set the information into the properties */
        emailProperties.put("mail.smtp.starttls.enable", "true");
        emailProperties.put("mail.smtp.host", emailHost);
        emailProperties.put("mail.smtp.user", emailUsername);
        emailProperties.put("mail.smtp.password", emailPassword);
        emailProperties.put("mail.smtp.port", "587"); // Google SMTP Port
        emailProperties.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(emailProperties);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(emailHost));
            InternetAddress[] toAddress = new InternetAddress[targetEmail.length];

            // To get the array of addresses
            for( int i = 0; i < targetEmail.length; i++ ) {
                toAddress[i] = new InternetAddress(targetEmail[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(emailHost, emailUsername, emailPassword);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }

    public void SendActivationEmail(String email){
        Random rand = new Random();
        int activationCode = rand.nextInt(90000) + 10000;
        String subject = "Canvas Application Activation Code!";
        String body = "Thank you for signing up! \n\nYour activation code is: " + activationCode;
        // Store it, so we can input it into the database
        this.setActivationCode(activationCode);
        SendEmail(new String[]{email}, subject, body);
    }
}
