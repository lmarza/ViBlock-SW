package View;

import Data.Entrata;
import javafx.scene.control.TableView;

import java.util.ArrayList;

public interface ViewRiepilogoGiornata {
    public void buildDaySummary(ArrayList<Entrata> records, TableView recordTableView);
}
