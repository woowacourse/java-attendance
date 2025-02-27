package attendance.domain;

import java.time.LocalTime;

public enum CampusOperatingTime {

    OPEN_AT(LocalTime.of(8, 0)),
    CLOSE_AT(LocalTime.of(23, 1))
    ;

    private final LocalTime time;

    CampusOperatingTime(LocalTime time) {
        this.time = time;
    }

    public LocalTime getTime() {
        return time;
    }

    public static boolean notInOperation(LocalTime inputTime) {
        return inputTime.isBefore(OPEN_AT.time) || !inputTime.isBefore(CLOSE_AT.time);
    }
}
