package domain;

import java.time.LocalTime;

public enum CampusOpenTime {
    OPEN_TIME(LocalTime.of(8, 0)),
    CLOSE_TIME(LocalTime.of(23, 0));

    private final LocalTime time;

    CampusOpenTime(LocalTime time) {
        this.time = time;
    }

    public static boolean cantAttendance(LocalTime attendanceTime) {
        return attendanceTime.isBefore(OPEN_TIME.time) || attendanceTime.isAfter(CLOSE_TIME.time);
    }
}
