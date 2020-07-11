package Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {

    public static void sendMail(String recepient) throws FileNotFoundException, MessagingException {
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");


        File loginf = new File("C:\\Users\\Luca Marzari\\Desktop\\logMail.txt");
        Scanner read = new Scanner(loginf);
        read.useDelimiter(",");
        String mail = null;
        String psw = null;

        while(read.hasNext()){
            mail = read.next();
            psw = read.next();
        }

        String finalMail = mail;
        String finalPsw = psw;
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(finalMail, finalPsw);
            }
        });

        Message message = prepareMessage(session, finalMail, recepient);
        Transport.send(message);
    }

    private static Message prepareMessage(Session session, String finalMail, String recepient) {
        try{

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(finalMail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("Invio password temporanea per accesso App Vi-Block");
            message.setText("Caro socio,\nLa psw temporanea è: Hello World!");

            return message;
        } catch (Exception e){
            Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

}
