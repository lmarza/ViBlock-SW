package Model;

import Data.Manager;
import Utils.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ModelDBPrelievo implements ModelPrelievo {
    private DatabaseConnection db = DatabaseConnection.getInstance();
    @Override
    public BigDecimal getWithdrawal() {
        BigDecimal withdrawal;

        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT importo " +
                "FROM prelievo " +
                "WHERE date_part('day'::text, istante) = date_part('day'::text, CURRENT_DATE) ");

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
            if (withdrawal == null)
                withdrawal = new BigDecimal(0.0);

            return withdrawal;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return null;
    }

    @Override
    public void insertWithdrawal(BigDecimal importo, Manager socio)
    {
        db.DBOpenConnection();
        db.executeSQLUpdate("INSERT INTO prelievo(istante, socio, importo) VALUES(CURRENT_TIMESTAMP, ?, ?::NUMERIC(6,2)); ", List.of(socio.getUsername(), importo.toString()));
    }

    @Override
    public void updateWithdrawal(BigDecimal importo, Manager socio) {
        db.DBOpenConnection();
        db.executeSQLUpdate("UPDATE public.prelievo " +
                "SET istante= CURRENT_TIMESTAMP, socio=?, importo= importo + ?::NUMERIC(7,2)" +
                "WHERE date_part('day'::text, istante) = date_part('day'::text, CURRENT_DATE)", List.of(socio.getUsername(), importo.toString()));
    }

}

