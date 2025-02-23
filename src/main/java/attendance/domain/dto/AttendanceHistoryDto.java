package attendance.domain.dto;

import attendance.domain.AttendanceHistory;
import attendance.domain.AttendanceType;
import java.time.LocalDateTime;

public class AttendanceHistoryDto {
    private final LocalDateTime attendanceTime;
    private final AttendanceType attendanceType;

    private AttendanceHistoryDto(LocalDateTime attendanceTime, AttendanceType attendanceType) {
        this.attendanceTime = attendanceTime;
        this.attendanceType = attendanceType;
    }

    public static AttendanceHistoryDto of(AttendanceHistory attendanceHistory) {
        return new AttendanceHistoryDto(attendanceHistory.getAttendanceDateTime(), attendanceHistory.getAttendanceType());
    }

    public LocalDateTime getAttendanceTime() {
        return attendanceTime;
    }

    public AttendanceType getAttendanceType() {
        return attendanceType;
    }
}
