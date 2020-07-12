package Model;

import Data.Person;
import Utils.DatabaseConnection;

import java.util.List;

public class ModelDBCliente implements ModelCliente {
    private DatabaseConnection db = DatabaseConnection.getInstance();


    @Override
    public void insertNewClient(Person person) {
        db.DBOpenConnection();
        if(person.getMedicalCertificate() == null)
            db.executeSQLUpdate( "INSERT INTO public.utente(nome, cognome, sesso, datanascita, mail, psw, certificatomedico, dataprova, tipoutente, " +
                    "luogonascita, cf) " +
                    "VALUES (?, ?, ?, ?::DATE,null, null, null,  ?::DATE, ?, ?, ?); ", List.of(person.getName().trim(), person.getSurname().trim(), person.getSex().trim(), person.getBirthday().trim(),
                    person.getDataTry().trim(), person.getClientType().trim(), person.getBornCity().trim(), person.getCf().trim()));
        else
            db.executeSQLUpdate( "INSERT INTO public.utente(nome, cognome, sesso, datanascita, mail, psw, certificatomedico, dataprova, tipoutente, " +
                    "luogonascita, cf) " +
                    "VALUES (?, ?, ?, ?::DATE, ?, ?, ?, ?::DATE, ?, ?, ?); ", List.of(person.getName().trim(), person.getSurname().trim(), person.getSex().trim(), person.getBirthday().trim(),
                    person.getMail().trim(),person.getPsw().trim(), person.getMedicalCertificate().toString().trim(), person.getDataTry().trim(), person.getClientType().trim(), person.getBornCity().trim(), person.getCf().trim()));
    }


}
