package attendance.domain;

import java.time.LocalDateTime;

public class AttendanceResult {
    private LocalDateTime attendanceTime;
    private String attendanceType;

    public AttendanceResult(LocalDateTime attendanceTime, String attendanceType) {
        this.attendanceTime = attendanceTime;
        this.attendanceType = attendanceType;
    }

    public LocalDateTime getAttendanceTime() {
        return attendanceTime;
    }

    public String getAttendanceType() {
        return attendanceType;
    }

    public void modify(LocalDateTime modifyLocalDateTime, String modifyAttendanceResult) {
        attendanceTime = modifyLocalDateTime;
        attendanceType = modifyAttendanceResult;
    }
}
