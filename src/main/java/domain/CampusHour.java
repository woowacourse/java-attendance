package domain;

import java.time.LocalTime;

public enum CampusHour {
    OPEN(LocalTime.of(8, 0)),
    CLOSE(LocalTime.of(23, 0));

    private final LocalTime time;

    CampusHour(LocalTime time) {
        this.time = time;
    }

    public static boolean isOperatingTime(LocalTime time) {
        return (time.isAfter(OPEN.time) && time.isBefore(CLOSE.time))
                || time.equals(OPEN.time)
                || time.equals(CLOSE.time);
    }
}
