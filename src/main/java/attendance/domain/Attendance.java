package attendance.domain;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record Attendance(LocalTime time, AttendanceStatus attendanceStatus) {
    public Attendance(LocalDateTime time) {
        this(time.toLocalTime(), AttendanceStatus.checkAttendance(time));
    }

    public int getHour() {
        return time.getHour();
    }

    public int getMinute() {
        return time.getMinute();
    }

}
