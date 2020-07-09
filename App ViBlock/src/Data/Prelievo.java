package Data;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Prelievo {
    Long istantePrelievo;
    BigDecimal importo;

    public Prelievo(){}

    public Long getIstantePrelievo() {
        return istantePrelievo;
    }

    public void setIstantePrelievo(Long istantePrelievo) {
        this.istantePrelievo = istantePrelievo;
    }

    public BigDecimal getImporto() {
        return importo;
    }

    public void setImporto(BigDecimal importo) {
        this.importo = importo;
    }

    public Prelievo(Long istantePrelievo, BigDecimal importo) {
        this.istantePrelievo = istantePrelievo;
        this.importo = importo;
    }

    public Date getDateFromUnixTime()
    {
        return new Date((long) istantePrelievo*1000);
    }

    public String RiepilogoGiornDate()
    {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
        return dateFormat.format(this.getDateFromUnixTime());
    }
}
