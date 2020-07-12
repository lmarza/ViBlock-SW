package Model;

import Data.Pagamento;
import Utils.DatabaseConnection;

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
}
