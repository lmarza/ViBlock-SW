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

    public static void sendMail(String recepient, String nome, String cognome,String tempPsw) throws FileNotFoundException, MessagingException {
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

        Message message = prepareMessage(session, finalMail, recepient, nome, cognome, tempPsw);
        Transport.send(message);
    }

    private static Message prepareMessage(Session session, String finalMail, String recepient, String nome, String cognome, String tempPsw) {
        try{

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(finalMail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("Invio password temporanea per accesso App Vi-Block");
            String text = String.format("Caro socio %s %s,\n\nGrazie per esserti iscritto al Vi Block!\nQuest'anno ci sono un sacco di " +
                    "novità che potranno rendere la tua esperienza più divertente e l'accesso alla palestra più semplice.\n" +
                    "Dal Play Store (Android) o App Store (Apple) potrai scaricare l'applicazione \"Vi-Block\" e accedere con le seguenti credenziali:\n " +
                    "-Username: il tuo codice fiscale\n" +
                    "-psw: %s\n\n" +
                    "L'applicazione ti permetterà di: restare aggiornato su tutte le novità che riguardano la palestra, poter prenotare gli slot di allenamento (causa " +
                    "restrizioni Covid), avere a disposizione un QRCODE per accesso rapido alla palestra in caso di abbonamento valido e tante altre funzionalità!\n" +
                    "Ti aspettiamo al più presto,\n\n" +
                    "Lo staff Vi-Block", nome, cognome, tempPsw);
            message.setText(text);

            return message;
        } catch (Exception e){
            Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

}
