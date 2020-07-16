package Model;

import Data.DayEntrance;
import Data.PeriodEntrance;
import Utils.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ModelDBPeriodEntrance implements ModelPeriodEntrance {
    private DatabaseConnection db = DatabaseConnection.getInstance();

    @Override
    public PeriodEntrance getPeriodEntrance(String cf) {
        PeriodEntrance periodEntrance;

        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT U.nome, U.cognome, U.cf, I.ingresso, I.inizioabbonamento, I.fineabbonamento " +
                "FROM ingressoadurata I JOIN utente U ON I.cfutente = U.cf " +
                "WHERE I.cfutente = ? ", List.of(cf));

        periodEntrance = resultSetToPeriodEntrance(db.getResultSet());


        return periodEntrance;
    }


    private PeriodEntrance resultSetToPeriodEntrance(ResultSet rs) {
        PeriodEntrance periodEntrance = null;

        try
        {
            while (rs.next())
            {
                periodEntrance = new PeriodEntrance();
                periodEntrance.setName(db.getSQLString(rs, "nome"));
                periodEntrance.setSurname(db.getSQLString(rs,"cognome"));
                periodEntrance.setCf(db.getSQLString(rs, "cf"));
                periodEntrance.setEntrance(db.getSQLString(rs,"ingresso"));
                periodEntrance.setStartSubmission(db.getSQLDate(rs, "inizioabbonamento"));
                periodEntrance.setEndSubmission(db.getSQLDate(rs, "fineabbonamento"));
            }

            return periodEntrance;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return null;
    }

    @Override
    public void insertNewMonthPeriodEntranceSubmission(String cf) {
        db.DBOpenConnection();
        db.executeSQLUpdate("INSERT INTO public.ingressoadurata(ingresso, istanteingresso, inizioabbonamento, fineabbonamento, cfutente) " +
                "VALUES ('Abbonamento mensile', CURRENT_TIMESTAMP, CURRENT_DATE, CURRENT_DATE + INTERVAL '1 month' , ?) ", List.of(cf));

    }

    @Override
    public void insertNew3MonthPeriodEntranceSubmission(String cf) {
        db.DBOpenConnection();
        db.executeSQLUpdate("INSERT INTO ingressoadurata(ingresso, istanteingresso, inizioabbonamento, fineabbonamento, cfutente) " +
                "VALUES ('Abbonamento trimestrale', CURRENT_TIMESTAMP, CURRENT_DATE, CURRENT_DATE + INTERVAL '1 month' , ?) ", List.of(cf));
    }

    @Override
    public boolean isAlreadyEntered(String cf) {
        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT COUNT(*) " +
                "FROM ingressoadurata " +
                "WHERE cfutente = ? AND istanteingresso::DATE = CURRENT_DATE", List.of(cf));

        int result = resultSetToisAlreadyEntered(db.getResultSet());

        return result != 0;
    }

    @Override
    public void insertOldMonthSubmission(String cf, int giorniResidui) {
        db.DBOpenConnection();
        db.executeSQLUpdate("INSERT INTO ingressoadurata(ingresso, istanteingresso, inizioabbonamento, fineabbonamento, cfutente) " +
                "VALUES ('Abbonamento mensile', CURRENT_TIMESTAMP, CURRENT_DATE, CURRENT_DATE + ?::INTEGER, ?); ", List.of(String.valueOf(giorniResidui),cf));
    }

    @Override
    public void insertOld3MonthSubmission(String cf, int giorniResidui) {
        db.DBOpenConnection();
        db.executeSQLUpdate("INSERT INTO ingressoadurata(ingresso, istanteingresso, inizioabbonamento, fineabbonamento, cfutente) " +
                "VALUES ('Abbonamento mensile', CURRENT_TIMESTAMP, CURRENT_DATE, CURRENT_DATE + ?::INTEGER, ?); ", List.of(String.valueOf(giorniResidui),cf));
    }

    private int resultSetToisAlreadyEntered(ResultSet rs) {

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
