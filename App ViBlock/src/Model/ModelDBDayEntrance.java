package Model;

import Data.DayEntrance;
import Data.Person;
import Utils.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ModelDBDayEntrance implements ModelDayEntrance {
    private DatabaseConnection db = DatabaseConnection.getInstance();

    @Override
    public DayEntrance getDayEntrance(String cf) {
        DayEntrance dayEntrance;

        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT U.nome, U.cognome, U.cf, I.ingresso, I.ingressiresidui " +
                "FROM ingressoagiorni I JOIN utente U ON I.cfutente = U.cf " +
                "WHERE I.cfutente = ?", List.of(cf));

        dayEntrance = resultSetToDayEntrance(db.getResultSet());


        return dayEntrance;
    }


    private DayEntrance resultSetToDayEntrance(ResultSet rs) {
        DayEntrance dayEntrance = null;

        try
        {
            while (rs.next())
            {
                dayEntrance = new DayEntrance();
                dayEntrance.setName(db.getSQLString(rs, "nome"));
                dayEntrance.setSurname(db.getSQLString(rs,"cognome"));
                dayEntrance.setCf(db.getSQLString(rs, "cf"));
                dayEntrance.setEntrance(db.getSQLString(rs,"ingresso"));
                dayEntrance.setRemainingEntrance(db.getSQLInt(rs,"ingressiresidui"));
            }

            return dayEntrance;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return null;
    }

    @Override
    public void insertNewDayEntranceSubmission(String entrance, String cf, int remainingEntrance) {
        db.DBOpenConnection();
        db.executeSQLUpdate("INSERT INTO public.ingressoagiorni(ingresso, istanteingresso, ingressiresidui, cfutente) " +
                "VALUES (?, CURRENT_TIMESTAMP, ?, ?); ",List.of(entrance, String.valueOf(remainingEntrance), cf));

    }
}
