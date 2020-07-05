package Model;

import Data.Manager;
import Utils.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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


}
