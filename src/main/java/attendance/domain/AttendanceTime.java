package attendance.domain;

import java.time.LocalTime;

public enum AttendanceTime {
    OPEN_TIME(LocalTime.of(8, 0)),
    CLOSE_TIME(LocalTime.of(23, 0)),
    MONDAY_LATE_TIME(LocalTime.of(13, 6)),
    MONDAY_ABSENCE_TIME(LocalTime.of(13, 31)),
    GENERAL_LATE_TIME(LocalTime.of(10, 6)),
    GENERAL_ABSENCE_TIME(LocalTime.of(10, 31));

    private final LocalTime time;

    AttendanceTime(LocalTime time) {
        this.time = time;
    }

    public LocalTime getTime() {
        return time;
    }
}
