package Model;

import Data.Person;

public interface ModelCliente {
    public void insertNewClient(Person person);
    public int checkIfClientAlreadyExists(String cf);
    public void updateClientInformation(Person person);
    public Person getClient(String cf);
    public Person getClientFromNameSurname(String name, String surname);

    public int checkTenEntranceSubmission(String cf);
    public int checkMonthEntranceSubmission(String cf);
    public int check3MonthEntranceSubmission(String cf);
    public int checkClassEntranceSubmission(String cf);

    void updateClientSubmissionDayEntrance(String cf);
    void updateClientSubmissionDurationEntrance(String cf);

}
