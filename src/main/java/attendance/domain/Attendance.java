package attendance.domain;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record Attendance(LocalDateTime dateTime, AttendanceStatus attendanceStatus) {

    private static final LocalTime defaultSchedule = LocalTime.of(10, 0);
    private static final int ABSENCE = 30;
    private static final int LATE = 5;

    public Attendance(LocalDateTime dateTime) {
        this(dateTime, decideAttendanceStatus(dateTime));
    }

    static AttendanceStatus decideAttendanceStatus(LocalDateTime dateTime) {
        var time = dateTime.toLocalTime();
        if (isAfter(time, ABSENCE)) {
            return AttendanceStatus.ABSENCE;
        }
        if (isAfter(time, LATE)) {
            return AttendanceStatus.LATE;
        }

        return AttendanceStatus.ATTENDANCE;
    }

    private static boolean isAfter(LocalTime time, int minutesToAdd) {
        return time.isAfter(Attendance.defaultSchedule.plusMinutes(minutesToAdd));
    }
}
