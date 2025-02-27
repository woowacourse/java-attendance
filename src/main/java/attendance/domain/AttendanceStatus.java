package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public enum AttendanceStatus {
    ATTENDANCE("출석"),
    LATE("지각"),
    ABSENCE("결석");

    private final String name;

    AttendanceStatus(final String name) {
        this.name = name;
    }

    public static AttendanceStatus fetchUserAttendanceStatus(final LocalDateTime localDateTime) {
        return AttendanceStatus.ATTENDANCE;
    }
}
