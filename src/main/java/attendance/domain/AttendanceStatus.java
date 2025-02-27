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
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
        if (dayOfWeek.getValue() == 1) {
            if (localDateTime.getHour() > 13 || (localDateTime.getHour() == 13 && localDateTime.getMinute() > 30)) {
                return ABSENCE;
            }
            if (localDateTime.getHour() == 13 && localDateTime.getMinute() > 5) {
                return LATE;
            }
            return ATTENDANCE;
        }
        if (localDateTime.getHour() > 10 || (localDateTime.getHour() == 10 && localDateTime.getMinute() > 30)) {
            return ABSENCE;
        }
        if (localDateTime.getHour() == 10 && localDateTime.getMinute() > 5) {
            return LATE;
        }
        return ATTENDANCE;
    }
}
