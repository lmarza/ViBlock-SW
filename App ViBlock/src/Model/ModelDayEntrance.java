package Model;

import Data.DayEntrance;

public interface ModelDayEntrance {
    public DayEntrance getDayEntrance(String cf);
    public void insertNewDayEntranceSubmission(String entrance, String cf, int remainingEntrance);
}
