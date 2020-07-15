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
                periodEntrance.setStartSubmission(db.getSQLDate(rs, "inzioabbonamento"));
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

}
