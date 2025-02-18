package attendance.domain;

import java.time.LocalDateTime;

public class AttendanceHistory {
    private LocalDateTime attendanceTime;
    private String attendanceResult;

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

    public void modify(LocalDateTime modifyLocalDateTime, String modifyAttendanceResult) {
        attendanceTime = modifyLocalDateTime;
        attendanceResult = modifyAttendanceResult;
    }
}
