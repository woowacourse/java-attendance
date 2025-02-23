package attendance.domain;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record Attendance(LocalDateTime dateTime, AttendanceStatus attendanceStatus) {

    private static final LocalTime defaultSchedule = LocalTime.of(10, 0);

    public Attendance(LocalDateTime dateTime) {
        this(dateTime, decideAttendanceStatus(dateTime));
    }

    static AttendanceStatus decideAttendanceStatus(LocalDateTime dateTime) {
        var time = dateTime.toLocalTime();
        if (time.isAfter(defaultSchedule.plusMinutes(30))) {
            return AttendanceStatus.ABSENCE;
        }

        if (time.isAfter(defaultSchedule.plusMinutes(5))) {
            return AttendanceStatus.LATE;
        }

        return AttendanceStatus.ATTENDANCE;
    }
}
