package Model;

import Data.Manager;
import Data.Person;
import Utils.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
                    "luogonascita, cf, datatesseramento) " +
                    "VALUES (?, ?, ?, ?::DATE, ?, ?, ?::INTEGER, ?, ?, ?, ?::DATE); ", List.of(person.getName().trim(), person.getSurname().trim(), person.getSex().trim(), person.getBirthday().trim(),
                    person.getMail().trim(),person.getPsw().trim(), person.getMedicalCertificate(), person.getClientType().trim(), person.getBornCity().trim(), person.getCf().trim(),person.getDataMembership()));
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

    @Override
    public void updateClientInformation(Person person) {
        db.DBOpenConnection();
        db.executeSQLUpdate("UPDATE utente " +
                "SET mail=?, psw=?, certificatomedico=?::INTEGER, tipoutente=? , datatesseramento=?::DATE " +
                "WHERE cf = ? ;", List.of(person.getMail(), person.getPsw(), person.getMedicalCertificate(),person.getClientType(), person.getDataMembership(), person.getCf()));
    }

    @Override
    public Person getClient(String cf) {
        Person person;

        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT * " +
                "FROM utente " +
                "WHERE cf = ?; ", List.of(cf));

        person = resultSetToPerson(db.getResultSet());


        return person;
    }

    @Override
    public Person getClientFromNameSurname(String name, String surname) {
        Person person;

        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT * " +
                "FROM utente " +
                "WHERE (nome ILIKE ? OR cognome ILIKE ?) AND (nome ILIKE ? OR cognome ILIKE ?) ", List.of(name,name,surname, surname));

        person = resultSetToPerson(db.getResultSet());


        return person;
    }


    private Person resultSetToPerson(ResultSet rs)
    {
        Person person = null;

        try
        {
            while (rs.next())
            {
                person = new Person();
                person.setName(db.getSQLString(rs,"nome"));
                person.setSurname(db.getSQLString(rs, "cognome"));
                person.setSex(db.getSQLString(rs, "sesso"));
                person.setBirthday(db.getSQLDate(rs,"datanascita"));
                person.setMail(db.getSQLString(rs,"mail"));
                person.setPsw(db.getSQLString(rs,"psw"));
                person.setMedicalCertificate(String.valueOf(db.getSQLInt(rs,"certificatomedico")));
                person.setDataTry(db.getSQLDate(rs,"dataprova"));
                person.setClientType(db.getSQLString(rs,"tipoutente"));
                person.setBornCity(db.getSQLString(rs, "luogonascita"));
                person.setCf(db.getSQLString(rs, "cf"));
                person.setDataMembership(db.getSQLString(rs,"datatesseramento"));
            }

            return person;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return null;
    }

    @Override
    public int checkTenEntranceSubmission(String cf) {
        int count;

        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT COUNT(*) " +
                "FROM ingressoagiorni " +
                "WHERE ingresso ILIKE 'Abbonamento 10 ingressi' AND cfutente = ? AND ingressiresidui > 0; ", List.of(cf));

        count = resultSetToCountRecord(db.getResultSet());

        return count;
    }

    @Override
    public int checkMonthEntranceSubmission(String cf) {
        int count;

        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT COUNT(*) " +
                "FROM ingressoadurata " +
                "WHERE ingresso ILIKE 'Abbonamento mensile' AND cfutente = ? AND fineabbonamento > CURRENT_DATE; ", List.of(cf));

        count = resultSetToCountRecord(db.getResultSet());

        return count;
    }

    @Override
    public int check3MonthEntranceSubmission(String cf) {
        int count;

        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT COUNT(*) " +
                "FROM ingressoadurata " +
                "WHERE ingresso ILIKE 'Abbonamento trimestrale' AND cfutente = ? AND fineabbonamento > CURRENT_DATE; ", List.of(cf));

        count = resultSetToCountRecord(db.getResultSet());

        return count;
    }

    @Override
    public int checkClassEntranceSubmission(String cf) {
        int count;

        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT COUNT(*) " +
                "FROM ingressoagiorni " +
                "WHERE ingresso ILIKE 'Tessera corso' AND cfutente = ? AND ingressiresidui > 0; ", List.of(cf));

        count = resultSetToCountRecord(db.getResultSet());

        return count;
    }

    @Override
    public void updateClientSubmissionDayEntrance(String cf) {
        db.DBOpenConnection();
        db.executeSQLUpdate("UPDATE ingressoagiorni " +
                "SET istanteingresso = CURRENT_TIMESTAMP, ingressiresidui = ingressiresidui - 1 " +
                "WHERE cfutente = ?",List.of(cf));

    }

    @Override
    public void updateClientSubmissionDurationEntrance(String cf) {
        db.DBOpenConnection();
        db.executeSQLUpdate("UPDATE ingressoadurata " +
                "SET istanteingresso = CURRENT_TIMESTAMP " +
                "WHERE cfutente = ?",List.of(cf));
    }


}


