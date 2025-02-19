package attendance.domain;

import java.time.LocalDateTime;

public class AttendanceResultDto {
    private final LocalDateTime attendanceTime;
    private final AttendanceType attendanceType;

    private AttendanceResultDto(LocalDateTime attendanceTime, AttendanceType attendanceType) {
        this.attendanceTime = attendanceTime;
        this.attendanceType = attendanceType;
    }

    public static AttendanceResultDto of(AttendanceResult attendanceResult) {
        return new AttendanceResultDto(attendanceResult.getAttendanceTime(), attendanceResult.getAttendanceType());
    }

    public LocalDateTime getAttendanceTime() {
        return attendanceTime;
    }

    public AttendanceType getAttendanceType() {
        return attendanceType;
    }
}
