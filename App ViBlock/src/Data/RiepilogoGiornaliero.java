package Data;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class RiepilogoGiornaliero {
    Long data;
    BigDecimal saldoFinale;
    Integer tesseramenti;
    BigDecimal soldiTesseramenti;
    Integer entrateGiornata;
    BigDecimal prelievi;

    public RiepilogoGiornaliero(){}

    public RiepilogoGiornaliero(Long data, BigDecimal saldoFinale, Integer tesseramenti, BigDecimal soldiTesseramenti, Integer entrateGiornata, BigDecimal prelievi) {
        this.data = data;
        this.saldoFinale = saldoFinale;
        this.tesseramenti = tesseramenti;
        this.soldiTesseramenti = soldiTesseramenti;
        this.entrateGiornata = entrateGiornata;
        this.prelievi = prelievi;
    }

    public Long getData() {
        return data;
    }

    public void setData(Long data) {
        this.data = data;
    }

    public BigDecimal getSaldoFinale() {
        return saldoFinale;
    }

    public void setSaldoFinale(BigDecimal saldoFinale) {
        this.saldoFinale = saldoFinale;
    }

    public Integer getTesseramenti() {
        return tesseramenti;
    }

    public void setTesseramenti(Integer tesseramenti) {
        this.tesseramenti = tesseramenti;
    }

    public BigDecimal getSoldiTesseramenti() {
        return soldiTesseramenti;
    }

    public void setSoldiTesseramenti(BigDecimal soldiTesseramenti) {
        this.soldiTesseramenti = soldiTesseramenti;
    }

    public Integer getEntrateGiornata() {
        return entrateGiornata;
    }

    public void setEntrateGiornata(Integer entrateGiornata) {
        this.entrateGiornata = entrateGiornata;
    }

    public BigDecimal getPrelievi() {
        return prelievi;
    }

    public void setPrelievi(BigDecimal prelievi) {
        this.prelievi = prelievi;
    }

    public Date getDateFromUnixTime()
    {
        return new Date((long) data*1000);
    }

    public String RiepilogoGiornDate()
    {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
        return dateFormat.format(this.getDateFromUnixTime());
    }
}
