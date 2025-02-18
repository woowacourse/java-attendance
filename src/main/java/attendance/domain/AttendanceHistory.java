package attendance.domain;

import java.time.LocalDateTime;

public class AttendanceHistory {
    private final LocalDateTime attendanceTime;
    private final String attendanceResult;

    public AttendanceHistory(LocalDateTime attendanceTime, String attendanceResult) {
        this.attendanceTime = attendanceTime;
        this.attendanceResult = attendanceResult;
    }

    public LocalDateTime getAttendanceTime() {
        return attendanceTime;
    }

    public String getAttendanceResult() {
        return attendanceResult;
    }
}
