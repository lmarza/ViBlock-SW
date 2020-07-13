package Model;

import Data.Person;

public interface ModelCliente {
    public void insertNewClient(Person person);
    public int checkIfClientAlreadyExists(String cf);
}
