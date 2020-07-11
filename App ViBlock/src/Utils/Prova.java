package Utils;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;

public class Prova {
    public static void main(String[] args) throws FileNotFoundException, MessagingException {
        Mail.sendMail("luca.marzari95@gmail.com");
    }
}
