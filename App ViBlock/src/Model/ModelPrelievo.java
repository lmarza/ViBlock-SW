package Model;

import Data.Manager;

import java.math.BigDecimal;

public interface ModelPrelievo {
    public BigDecimal getWithdrawal();
    public void insertWithdrawal(BigDecimal importo, Manager socio);
    public void updateWithdrawal(BigDecimal importo, Manager socio);
}
