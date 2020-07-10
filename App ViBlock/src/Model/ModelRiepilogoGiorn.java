package Model;

import Data.RiepilogoGiornaliero;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface ModelRiepilogoGiorn {
    public BigDecimal getOpeningBalance();
    public BigDecimal getDayBalance();
    public ArrayList<RiepilogoGiornaliero> getDailySummaries();
    public ArrayList<RiepilogoGiornaliero> getMonthSummaries(int month);
}
