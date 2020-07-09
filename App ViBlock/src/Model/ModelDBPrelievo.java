package Model;

import Utils.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModelDBPrelievo implements ModelPrelievo {
    private DatabaseConnection db = DatabaseConnection.getInstance();
    @Override
    public BigDecimal getWithdrawal() {
        BigDecimal withdrawal;

        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT importo " +
                "FROM prelievo " +
                "WHERE date_part('day'::text, istanteprelievo) = date_part('day'::text, CURRENT_DATE) ");

        withdrawal = resultSetTowithdrawal(db.getResultSet());

        return withdrawal;

    }

    private BigDecimal resultSetTowithdrawal(ResultSet rs) {

        BigDecimal withdrawal = null;

        try
        {
            while (rs.next())
            {
                withdrawal = db.getSQLNumeric(rs,"importo");
            }

            return withdrawal;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return null;
    }



}

