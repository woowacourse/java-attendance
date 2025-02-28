package domain;

import java.time.LocalTime;

public enum CampusTime {
    START_TIME(LocalTime.of(8, 0)),
    END_TIME(LocalTime.of(23, 0)),
    ;
    private final LocalTime time;

    CampusTime(LocalTime time) {
        this.time = time;
    }

    public static boolean isAfterStartTime(LocalTime time) {
        return START_TIME.time.isAfter(LocalTime.from(time));
    }

    public static boolean isBeforeEndTime(LocalTime time) {
        return END_TIME.time.isBefore(LocalTime.from(time));
    }
}
