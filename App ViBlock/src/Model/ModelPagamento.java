package Model;

import Data.Pagamento;

import java.math.BigDecimal;

public interface ModelPagamento {
    public void insertNewPayment(Pagamento payment);
    public BigDecimal getPriceEntrance(String typeEntrance, String u18);
}
