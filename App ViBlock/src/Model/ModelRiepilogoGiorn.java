package Model;

import Data.RiepilogoGiornaliero;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface ModelRiepilogoGiorn {
    public BigDecimal getOpeningBalance();
    public BigDecimal getDayBalance();
    public ArrayList<RiepilogoGiornaliero> getDailySummaries();
    public ArrayList<RiepilogoGiornaliero> getMonthSummaries(String month);
    public int checkIfRecordExists();
    public int getDailyMembership();
    public void insertDayBalance(RiepilogoGiornaliero dailyBalance);
    public void updateDayBalance(RiepilogoGiornaliero dailyBalance);
}
