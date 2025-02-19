package attendance.domain;

import java.time.LocalDateTime;

public record HourMinute(int hour, int minute, AttendanceStatus attendanceStatus) {
    public HourMinute(LocalDateTime localDateTime) {
        this(localDateTime.getHour(), localDateTime.getMinute(), AttendanceChecker.checkAttendance(localDateTime));
    }
}
