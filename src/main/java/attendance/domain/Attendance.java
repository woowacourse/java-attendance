package attendance.domain;

import java.time.LocalDateTime;

public class Attendance {
    private final AttendanceStatus attendanceStatus;
    private LocalDateTime time;

    public Attendance(LocalDateTime time, AttendanceStatus attendanceStatus) {
        this.time = time;
        this.attendanceStatus = attendanceStatus;
    }

    public LocalDateTime getAttendanceTime() {
        return LocalDateTime.of(time.toLocalDate(), time.toLocalTime());
    }

    public AttendanceStatus getAttendanceStatus() {
        return attendanceStatus;
    }
}
