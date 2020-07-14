package Model;

import Data.Pagamento;
import Utils.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ModelDBPagamento implements ModelPagamento{
    private DatabaseConnection db = DatabaseConnection.getInstance();
    @Override
    public void insertNewPayment(Pagamento payment) {
        db.DBOpenConnection();
        if(payment.getMembership().equalsIgnoreCase("NO") && payment.getShoes().equalsIgnoreCase("NO"))
            db.executeSQLUpdate("INSERT INTO pagamento(istantepagamento, importo, tipopagamento, ingresso, tesseramento, prezzotesseramento, scarpette, prezzoscarpette, cfutente)" +
            "VALUES (?::TIMESTAMP, ?::NUMERIC(6,2), ?, ?, ?, null, ?, null, ?); ", List.of(payment.getPaymentInstant().toString(), payment.getAmount().toString(),payment.getPaymentType(), payment.getEnterType(),
                    payment.getMembership(), payment.getShoes(),payment.getCfPerson()));
        else if(payment.getMembership().equalsIgnoreCase("NO") && payment.getShoes().equalsIgnoreCase("SI"))
            db.executeSQLUpdate("INSERT INTO pagamento(istantepagamento, importo, tipopagamento, ingresso, tesseramento, prezzotesseramento, scarpette, prezzoscarpette, cfutente)" +
                    "VALUES (?::TIMESTAMP, ?::NUMERIC(6,2), ?, ?, ?, null, ?, ?::NUMERIC(6,2), ?); ", List.of(payment.getPaymentInstant().toString(), payment.getAmount().toString(),payment.getPaymentType(), payment.getEnterType(),
                    payment.getMembership(), payment.getShoes(),payment.getPriceShoes().toString(), payment.getCfPerson()));
        else if(payment.getMembership().equalsIgnoreCase("SI") && payment.getShoes().equalsIgnoreCase("NO"))
            db.executeSQLUpdate("INSERT INTO pagamento(istantepagamento, importo, tipopagamento, ingresso, tesseramento, prezzotesseramento, scarpette, prezzoscarpette, cfutente)" +
                    "VALUES (?::TIMESTAMP, ?::NUMERIC(6,2), ?, ?, ?, ?::NUMERIC(6,2), ?, null, ?); ", List.of(payment.getPaymentInstant().toString(), payment.getAmount().toString(),payment.getPaymentType(), payment.getEnterType(),
                    payment.getMembership(), payment.getPriceMembership().toString(), payment.getShoes(), payment.getCfPerson()));
        else
            db.executeSQLUpdate("INSERT INTO pagamento(istantepagamento, importo, tipopagamento, ingresso, tesseramento, prezzotesseramento, scarpette, prezzoscarpette, cfutente)" +
                    "VALUES (?::TIMESTAMP, ?::NUMERIC(6,2), ?, ?, ?, ?::NUMERIC(6,2), ?, ?::NUMERIC(6,2), ?); ", List.of(payment.getPaymentInstant().toString(), payment.getAmount().toString(),payment.getPaymentType(), payment.getEnterType(),
                    payment.getMembership(), payment.getPriceMembership().toString(), payment.getShoes(),payment.getPriceShoes().toString(), payment.getCfPerson()));

    }

    @Override
    public BigDecimal getPriceEntrance(String typeEntrance, String u18) {
        BigDecimal price;

        db.DBOpenConnection();
        if(u18.equalsIgnoreCase("SI"))
            db.executeSQLQuery( "SELECT prezzominorenne as prezzo " +
                    "FROM tipoingresso " +
                    "WHERE nome ILIKE ? ", List.of(typeEntrance));
        else
            db.executeSQLQuery( "SELECT prezzomaggiorenne as prezzo " +
                    "FROM tipoingresso " +
                    "WHERE nome ILIKE ? ", List.of(typeEntrance));

        price = resultSetToPrice(db.getResultSet());

        return price;

    }

    private BigDecimal resultSetToPrice(ResultSet rs) {

        BigDecimal price = null;

        try
        {
            while (rs.next())
            {
                price = db.getSQLNumeric(rs,"prezzo");
            }

            return price;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return null;
    }
}
