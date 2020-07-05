package Data;

public class Manager {
    String username;
    String psw;
    String name;
    String surname;

    public Manager(){}

    public Manager(String username, String psw, String name, String surname) {
        this.username = username;
        this.psw = psw;
        this.name = name;
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
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

    @Override
    public String toString()
    {
        return username;
    }

    public String toStringNameSurname()
    {
        return name + " " + surname;
    }
}
