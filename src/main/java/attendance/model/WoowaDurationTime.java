package attendance.model;

import java.time.LocalTime;

public enum WoowaDurationTime {
    ;

    public static boolean isDurationTime(LocalTime time) {
        return !(time.isBefore(LocalTime.of(8, 0)) || time.isAfter(LocalTime.of(23, 0)));
    }
}
