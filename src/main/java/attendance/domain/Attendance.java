package attendance.domain;

import java.time.LocalTime;

public record Attendance(AttendanceStatus attendanceStatus, LocalTime time) {

    public String getStatus() {
        return attendanceStatus.getStatus();
    }
}
