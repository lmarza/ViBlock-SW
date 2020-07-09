package Model;

import Data.Entrata;
import Utils.DatabaseConnection;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ModelDBEntrata implements ModelEntrata {
    private DatabaseConnection db = DatabaseConnection.getInstance();
    @Override
    public ArrayList<Entrata> getAllRecord() {
        ArrayList<Entrata> records = new ArrayList<>();

        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT * " +
                "FROM riepilogogiornata ");

        records = resultSetToRecords(db.getResultSet());


        return records;
    }


    private ArrayList<Entrata> resultSetToRecords(ResultSet rs) {
        ArrayList<Entrata> records = new ArrayList<>();
        Entrata record;

        try
        {
            while (rs.next())
            {
                String under18;
                if(!db.getSQLBool(rs, "under18"))
                    under18 = "NO";
                else
                    under18 = "SI";

                record = new Entrata(db.getSQLString(rs,"nome"), db.getSQLString(rs,"cognome"), db.getSQLString(rs,"sesso"),
                        under18, db.getSQLString(rs,"ingresso"),db.getSQLString(rs, "tipopagamento"),
                        db.getSQLNumeric(rs, "importo"), db.getSQLString(rs,"tesseramento"), db.getSQLString(rs, "scarpette"));

                records.add(record);
            }

            return records;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return null;
    }

    @Override
    public int getPeopleEntered() {
        int nPeople;

        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT COUNT (DISTINCT (nome, cognome)) as numeropersone " +
                "FROM riepilogogiornata ");

        nPeople = resultSetToPeopleEntered(db.getResultSet());


        return nPeople;

    }

    private int resultSetToPeopleEntered(ResultSet rs) {
        int nPeople = 0;

        try
        {
            while (rs.next())
            {
                nPeople = db.getSQLInt(rs, "numeropersone");
            }

            return nPeople;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return Integer.parseInt(null);

    }
}
