package attendance.domain;

import java.time.LocalTime;

public enum CampusOperatingTime {

    OPEN(LocalTime.of(8, 0)),
    CLOSE(LocalTime.of(23, 0))
    ;

    private final LocalTime time;

    CampusOperatingTime(LocalTime time) {
        this.time = time;
    }

    public LocalTime getTime() {
        return time;
    }

    public static boolean notInOperation(LocalTime inputTime) {
        return inputTime.isBefore(OPEN.time) || inputTime.isAfter(CLOSE.time);
    }
}
