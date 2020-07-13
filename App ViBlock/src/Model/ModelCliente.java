package Model;

import Data.Person;

public interface ModelCliente {
    public void insertNewClient(Person person);
    public int checkIfClientAlreadyExists(String cf);
    public void updateClientInformation(Person person);
    public Person getClient(String cf);
}
