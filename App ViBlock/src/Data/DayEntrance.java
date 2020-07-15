package Data;

public class DayEntrance {
    private String name;
    private String surname;
    private String cf;
    private String entrance;
    private int remainingEntrance;

    public DayEntrance() {
    }

    public DayEntrance(String name, String surname, String cf, String entrance, int remainingEntrance) {
        this.name = name;
        this.surname = surname;
        this.cf = cf;
        this.entrance = entrance;
        this.remainingEntrance = remainingEntrance;
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

    public int getRemainingEntrance() {
        return remainingEntrance;
    }

    public void setRemainingEntrance(int remainingEntrance) {
        this.remainingEntrance = remainingEntrance;
    }
}
