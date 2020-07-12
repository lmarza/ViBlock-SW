package Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Pagamento {
    private String cfPerson;
    private Timestamp paymentInstant;
    private BigDecimal amount;
    private String paymentType;
    private String enterType;
    private String membership;
    private BigDecimal priceMembership;
    private String shoes;
    private BigDecimal priceShoes;

    public Pagamento() {}

    public Pagamento(String cfPerson, Timestamp paymentInstant, BigDecimal amount, String paymentType, String enterType, String membership, BigDecimal priceMembership, String shoes, BigDecimal priceShoes) {
        this.cfPerson = cfPerson;
        this.paymentInstant = paymentInstant;
        this.amount = amount;
        this.paymentType = paymentType;
        this.enterType = enterType;
        this.membership = membership;
        this.priceMembership = priceMembership;
        this.shoes = shoes;
        this.priceShoes = priceShoes;
    }

    public String getCfPerson() {
        return cfPerson;
    }

    public void setCfPerson(String cfPerson) {
        this.cfPerson = cfPerson;
    }

    public Timestamp getPaymentInstant() {
        return paymentInstant;
    }

    public void setPaymentInstant(Timestamp paymentInstant) {
        this.paymentInstant = paymentInstant;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getEnterType() {
        return enterType;
    }

    public void setEnterType(String enterType) {
        this.enterType = enterType;
    }

    public String getMembership() {
        return membership;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    public BigDecimal getPriceMembership() {
        return priceMembership;
    }

    public void setPriceMembership(BigDecimal priceMembership) {
        this.priceMembership = priceMembership;
    }

    public String getShoes() {
        return shoes;
    }

    public void setShoes(String shoes) {
        this.shoes = shoes;
    }

    public BigDecimal getPriceShoes() {
        return priceShoes;
    }

    public void setPriceShoes(BigDecimal priceShoes) {
        this.priceShoes = priceShoes;
    }
}
