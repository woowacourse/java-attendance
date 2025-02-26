package attendance.model;

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
        return false;
    }
}
