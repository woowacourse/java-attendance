package attendance.dto;

import attendance.domain.Attendance;
import attendance.domain.AttendanceStatus;
import java.time.LocalDateTime;

public record AttendanceResultResponse(
        LocalDateTime dateTime,
        AttendanceStatus status
) {
    public static AttendanceResultResponse from(final Attendance attendance) {
        return new AttendanceResultResponse(attendance.getAttendanceDateTime(), attendance.checkAttendanceStatus());
    }
}
