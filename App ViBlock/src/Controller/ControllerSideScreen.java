package Controller;

import Data.Manager;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class ControllerSideScreen {

    public void fillLabel(ArrayList<Manager> managers, Label user1Label, Label user2Label, Label saldoAperturaLabel, Label saldoGiorLabel, Label totCassaLabel, Label PersEntrateLabel, Label prelievoLabel )
    {
        if (managers.size() > 1)
        {
            user1Label.setText(managers.get(0).getUsername());
            user2Label.setText(managers.get(1).getUsername());
        }
        else
            user1Label.setText(managers.get(0).getUsername());
    }



}
