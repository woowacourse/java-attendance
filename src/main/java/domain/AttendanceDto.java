package domain;

import java.time.LocalDateTime;

public record AttendanceDto(LocalDateTime attendanceDateTime, AttendanceStatus attendanceStatus) {
    public static AttendanceDto from(Attendance attendance) {
        return new AttendanceDto(attendance.getLocalDateTime(), attendance.getAttendanceStatus());
    }
}
