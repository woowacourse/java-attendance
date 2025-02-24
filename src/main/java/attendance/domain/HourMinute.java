package attendance.domain;

import java.time.LocalDateTime;

public record HourMinute(int hour, int minute, AttendanceStatus attendanceStatus) {
    public static final int NULL_TIME = -1;

    public HourMinute(LocalDateTime localDateTime) {
        this(localDateTime.getHour(), localDateTime.getMinute(), AttendanceStatus.checkAttendance(localDateTime));
    }
}
