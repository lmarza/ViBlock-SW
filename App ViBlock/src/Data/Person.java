package Data;

public class Person {

    private String surname;
    private String name;
    private int dayBirth;
    private int monthBirth;
    private int yearBirth;
    private String c_born;
    private String sex;
    private String mail;
    private String psw;
    private String cf;
    private String birthday;
    private String medicalCertificate;
    private String dataTry;
    private String ClientType;
    private String dataMembership;


    public Person(){}

    public void setSurname(String s){
        this.surname=s;
    }

    public void setName(String s){
        this.name=s;
    }

    public void setDay(String d){
        this.dayBirth=Integer.parseInt(d);
    }

    public void setMonth(String d){
        this.monthBirth=Integer.parseInt(d);
    }

    public void setYear(String d){
        this.yearBirth=Integer.parseInt(d);
    }

    public void setBornCity(String s){
        this.c_born=s;
    }

    public void setSex(String sex){
        this.sex=sex;
    }

    public String getSurname(){
        return surname;
    }

    public String getName(){
        return name;
    }

    public String getBornCity(){
        return c_born;
    }

    public int getDay(){
        return dayBirth;
    }

    public int getMonth(){
        return monthBirth;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public int getYear(){
        return yearBirth;
    }

    public String getSex(){
        return sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getMedicalCertificate() {
        return medicalCertificate;
    }

    public void setMedicalCertificate(String medicalCertificate) {
        this.medicalCertificate = medicalCertificate;
    }

    public String getDataTry() {
        return dataTry;
    }

    public void setDataTry(String dataTry) {
        this.dataTry = dataTry;
    }

    public String getClientType() {
        return ClientType;
    }

    public void setClientType(String clientType) {
        ClientType = clientType;
    }

    public String getDataMembership() {
        return dataMembership;
    }

    public void setDataMembership(String dataMembership) {
        this.dataMembership = dataMembership;
    }

}
