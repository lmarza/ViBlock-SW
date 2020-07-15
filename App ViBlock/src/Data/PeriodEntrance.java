package Data;

public class PeriodEntrance {
    private String name;
    private String surname;
    private String cf;
    private String entrance;
    private String startSubmission;
    private String endSubmission;

    public PeriodEntrance() {
    }

    public PeriodEntrance(String name, String surname, String cf, String entrance, String startSubmission, String endSubmission) {
        this.name = name;
        this.surname = surname;
        this.cf = cf;
        this.entrance = entrance;
        this.startSubmission = startSubmission;
        this.endSubmission = endSubmission;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getEntrance() {
        return entrance;
    }

    public void setEntrance(String entrance) {
        this.entrance = entrance;
    }

    public String getStartSubmission() {
        return startSubmission;
    }

    public void setStartSubmission(String startSubmission) {
        this.startSubmission = startSubmission;
    }

    public String getEndSubmission() {
        return endSubmission;
    }

    public void setEndSubmission(String endSubmission) {
        this.endSubmission = endSubmission;
    }
}
