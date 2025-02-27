package attendance.domain;

import java.time.LocalDateTime;

public class AttendanceTime {
    private final LocalDateTime attendanceTime;

    public AttendanceTime(final LocalDateTime attendanceTime) {
        this.attendanceTime = attendanceTime;
    }

    public LocalDateTime getAttendanceTime() {
        return attendanceTime;
    }
}
