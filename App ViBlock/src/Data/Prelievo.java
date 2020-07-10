package Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Prelievo {
    Timestamp istantePrelievo;
    Manager socio;
    BigDecimal importo;

    public Prelievo(){}

    public Prelievo(Timestamp istantePrelievo, Manager socio, BigDecimal importo) {
        this.istantePrelievo = istantePrelievo;
        this.socio = socio;
        this.importo = importo;
    }

    public Manager getSocio() {
        return socio;
    }

    public void setSocio(Manager socio) {
        this.socio = socio;
    }

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



}
