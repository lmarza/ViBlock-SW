package Data;

import java.math.BigDecimal;

public class Entrata {
    String nome;
    String cognome;
    String sesso;
    String under18;
    String ingresso;
    String tipopagamento;
    BigDecimal importo;
    String tesseramento;
    String scarpette;

    public Entrata(){}

    public Entrata(String nome, String cognome, String sesso, String under18, String ingresso, String tipoPagamento, BigDecimal importo, String tesseramento, String scarpette) {
        this.nome = nome;
        this.cognome = cognome;
        this.sesso = sesso;
        this.under18 = under18;
        this.ingresso = ingresso;
        this.tipopagamento = tipoPagamento;
        this.importo = importo;
        this.tesseramento = tesseramento;
        this.scarpette = scarpette;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getSesso() {
        return sesso;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public String getUnder18() {
        return under18;
    }

    public void setUnder18(String under18) {
        this.under18 = under18;
    }

    public String getIngresso() {
        return ingresso;
    }

    public void setIngresso(String ingresso) {
        this.ingresso = ingresso;
    }

    public String getTipopagamento() {
        return tipopagamento;
    }

    public void setTipopagamento(String tipopagamento) {
        this.tipopagamento = tipopagamento;
    }

    public BigDecimal getImporto() {
        return importo;
    }

    public void setImporto(BigDecimal importo) {
        this.importo = importo;
    }

    public String getTesseramento() {
        return tesseramento;
    }

    public void setTesseramento(String tesseramento) {
        this.tesseramento = tesseramento;
    }

    public String getScarpette() {
        return scarpette;
    }

    public void setScarpette(String scarpette) {
        this.scarpette = scarpette;
    }

    @Override
    public String toString() {
        return
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", sesso='" + sesso + '\'' +
                ", under18=" + under18 +
                ", ingresso='" + ingresso + '\'' +
                ", tipoPagamento='" + tipopagamento + '\'' +
                ", importo=" + importo +
                ", tesseramento='" + tesseramento + '\'' +
                ", scarpette='" + scarpette + '\'' ;
    }
}
