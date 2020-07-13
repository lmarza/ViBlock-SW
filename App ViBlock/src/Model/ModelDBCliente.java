package Model;

import Data.Person;
import Utils.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
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
            db.executeSQLUpdate( "INSERT INTO public.utente(nome, cognome, sesso, datanascita, mail, psw, certificatomedico, tipoutente, " +
                    "luogonascita, cf) " +
                    "VALUES (?, ?, ?, ?::DATE, ?, ?, ?::INTEGER, ?, ?, ?); ", List.of(person.getName().trim(), person.getSurname().trim(), person.getSex().trim(), person.getBirthday().trim(),
                    person.getMail().trim(),person.getPsw().trim(), person.getMedicalCertificate(), person.getClientType().trim(), person.getBornCity().trim(), person.getCf().trim()));
    }

    @Override
    public int checkIfClientAlreadyExists(String cf) {
        int count;

        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT COUNT(*) " +
                "FROM utente " +
                "WHERE cf = ?; ", List.of(cf));

        count = resultSetToCountRecord(db.getResultSet());

        return count;
    }

    private int resultSetToCountRecord(ResultSet rs) {
        int count = 0;

        try
        {
            while (rs.next())
            {
                count = db.getSQLInt(rs, "count");
            }

            return count;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return Integer.parseInt(null);
    }


}
