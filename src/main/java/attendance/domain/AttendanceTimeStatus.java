package attendance.domain;

import java.time.LocalDateTime;

public record AttendanceTimeStatus(int hour, int minute, AttendanceStatus status) {
    public static final int NULL_TIME = -1;

    public AttendanceTimeStatus(LocalDateTime localDateTime) {
        this(localDateTime.getHour(), localDateTime.getMinute(), AttendanceChecker.checkAttendance(localDateTime));
    }
}
