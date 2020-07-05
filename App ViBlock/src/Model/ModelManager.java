package Model;
import Data.Manager;

import java.util.ArrayList;

public interface ModelManager {
    public ArrayList<Manager> getManagers();
    public Manager getManagerInformation(String username);
}
