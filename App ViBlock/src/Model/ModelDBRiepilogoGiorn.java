package Model;

import Data.Manager;
import Data.RiepilogoGiornaliero;
import Utils.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModelDBRiepilogoGiorn implements ModelRiepilogoGiorn {
    private DatabaseConnection db = DatabaseConnection.getInstance();

    @Override
    public BigDecimal getOpeningBalance()
    {

        BigDecimal openingBalance;

        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT saldoFinale " +
                            "FROM riepilogoGiornate " +
                            "ORDER BY data DESC " +
                            "LIMIT 1 " );

        openingBalance = resultSetToOpeningBalance(db.getResultSet());


        return openingBalance;
    }


    private BigDecimal resultSetToOpeningBalance(ResultSet rs) {

        BigDecimal openingBalance = null;

        try
        {
            while (rs.next())
            {
                openingBalance = db.getSQLNumeric(rs,"saldofinale");
            }

            return openingBalance;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return null;
    }

    @Override
    public BigDecimal getDayBalance() {
        BigDecimal dayBalance;

        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT SUM(importo) as saldogiornaliero " +
                "FROM riepilogogiornata ");

        dayBalance = resultSetToDayBalance(db.getResultSet());

        return dayBalance;
    }



    private BigDecimal resultSetToDayBalance(ResultSet rs) {

        BigDecimal dayBalance = null;

        try
        {
            while (rs.next())
            {
                dayBalance = db.getSQLNumeric(rs,"saldogiornaliero");
            }

            if (dayBalance == null)
                dayBalance = new BigDecimal(0.0);

            return dayBalance;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return null;
    }

    @Override
    public ArrayList<RiepilogoGiornaliero> getDailySummaries()
    {
        ArrayList<RiepilogoGiornaliero> dailySummaries = new ArrayList<>();

        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT * " +
                            "FROM riepilogogiornate " +
                            "ORDER BY DATA ");

        dailySummaries = resultSetToDailySummaries(db.getResultSet());


        return dailySummaries;
    }

    private ArrayList<RiepilogoGiornaliero> resultSetToDailySummaries(ResultSet rs) {
        ArrayList<RiepilogoGiornaliero> dailySummaries = new ArrayList<>();
        RiepilogoGiornaliero dailySummary = null;

        try
        {
            while (rs.next())
            {
                dailySummary = new RiepilogoGiornaliero();
                dailySummary.setData(db.getSQLDate(rs,"data"));
                dailySummary.setSaldoFinale(db.getSQLNumeric(rs,"saldofinale"));
                dailySummary.setTesseramenti(db.getSQLInt(rs,"tesseramenti"));
                dailySummary.setSoldiTesseramenti(db.getSQLNumeric(rs,"solditesseramenti"));
                dailySummary.setEntrateGiornata(db.getSQLNumeric(rs, "entrategiornaliere"));
                dailySummary.setPrelievi(db.getSQLNumeric(rs,"prelievi"));

                dailySummaries.add(dailySummary);

            }

            return dailySummaries;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return null;
    }

    @Override
    public ArrayList<RiepilogoGiornaliero> getMonthSummaries(String month) {
        ArrayList<RiepilogoGiornaliero> dailySummaries;

        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT * " +
                "FROM riepilogogiornate " +
                "WHERE date_part('month'::text, data) = ?::DOUBLE PRECISION " +
                "ORDER BY DATA ", List.of(month));

        dailySummaries = resultSetToDailySummaries(db.getResultSet());


        return dailySummaries;
    }

}
