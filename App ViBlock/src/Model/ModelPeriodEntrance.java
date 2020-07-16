package Model;

import Data.PeriodEntrance;

public interface ModelPeriodEntrance {
    public PeriodEntrance getPeriodEntrance(String cf);
    public void insertNewMonthPeriodEntranceSubmission(String cf);
    public void insertNew3MonthPeriodEntranceSubmission(String cf);
    public boolean isAlreadyEntered(String cf);

    void insertOldMonthSubmission(String cf, int giorniResidui);

    void insertOld3MonthSubmission(String cf, int giorniResidui);
}
