package attendance.domain;

import java.time.LocalDateTime;

public record Attendance(AttendanceStatus attendanceStatus, LocalDateTime dateTime) {

    public Attendance(LocalDateTime dateTime) {
        this(AttendanceStatus.ATTENDANCE, dateTime);
    }
}
