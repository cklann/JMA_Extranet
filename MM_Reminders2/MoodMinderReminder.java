import java.util.*;
import java.io.*;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public final class MoodMinderReminder
{
    public static void sendReminders()
    {
        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(new File("EmailAddresses.txt"));
        } catch (FileNotFoundException ex) {
            System.out.println(ex+" *** file not found ");
        }
        
        final String username = "support.team@jodymichael.com";
        final String password = "st0516ST!";
 
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "server.jodymichael.com");
        props.put("mail.smtp.port", "587");
 
        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
          });
 
        while (fileScanner.hasNext()) {
            try {
                String sendTo = fileScanner.nextLine();
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("support.team@jodymichael.com"));
                message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(sendTo));
                message.setSubject("Automatic reminder to update MoodMinder!");
                message.setText("Dear JMA Client,"
                    + "\n\n Please update your MoodMinder.");
 
                Transport.send(message);
                System.out.println("Message sent to " + sendTo);
 
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main (String[ ] args)
    {
        sendReminders();
    }
}