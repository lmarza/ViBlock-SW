package Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Prelievo {
    Timestamp istantePrelievo;
    BigDecimal importo;

    public Prelievo(){}

    public Timestamp getIstantePrelievo() {
        return istantePrelievo;
    }

    public void setIstantePrelievo(Timestamp istantePrelievo) {
        this.istantePrelievo = istantePrelievo;
    }

    public BigDecimal getImporto() {
        return importo;
    }

    public void setImporto(BigDecimal importo) {
        this.importo = importo;
    }

    public Prelievo(Timestamp istantePrelievo, BigDecimal importo) {
        this.istantePrelievo = istantePrelievo;
        this.importo = importo;
    }

}
