package View;

import Data.Entrata;
import Data.RiepilogoGiornaliero;
import javafx.scene.control.TableView;

import java.util.ArrayList;

public interface ViewRiepilogoMensile {
    public void buildMonthSummary(ArrayList<RiepilogoGiornaliero> dailySummaries, TableView recordTableView);
}
