package Model;
import Data.Manager;

import java.util.ArrayList;

public interface ModelManager {
    public ArrayList<Manager> getManagers();
    public Manager checkManagerInformation(String username, String password);
    public Manager getManagerInformation(String username);
}
