package attendance.model;

import java.time.DayOfWeek;
import java.time.LocalDate;

public enum WoowaDuration {
    MONDAY(),
    TUESDAY(),
    WEDNESDAY(),
    THURSDAY(),
    FRIDAY(),
    SATURDAY(),
    SUNDAY(),
    ;

    public static boolean isDurationDay(LocalDate date) {
        if (
                date.getDayOfWeek().equals(DayOfWeek.SUNDAY) ||
                        date.getDayOfWeek().equals(DayOfWeek.SATURDAY) ||
                        date.getDayOfMonth() == 25
        ) {
            return false;
        }
        return true;
    }
}
