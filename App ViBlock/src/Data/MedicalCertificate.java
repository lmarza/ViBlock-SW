package Data;

public class MedicalCertificate {
    private int id;
    private String dataRilascio;
    private String dataScadenza;
    private String pathImmagine;

    public MedicalCertificate() {
    }

    public MedicalCertificate(int id, String dataRilascio, String dataScadenza, String pathImmagine) {
        this.id = id;
        this.dataRilascio = dataRilascio;
        this.dataScadenza = dataScadenza;
        this.pathImmagine = pathImmagine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDataRilascio() {
        return dataRilascio;
    }

    public void setDataRilascio(String dataRilascio) {
        this.dataRilascio = dataRilascio;
    }

    public String getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(String dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public String getPathImmagine() {
        return pathImmagine;
    }

    public void setPathImmagine(String pathImmagine) {
        this.pathImmagine = pathImmagine;
    }
}
