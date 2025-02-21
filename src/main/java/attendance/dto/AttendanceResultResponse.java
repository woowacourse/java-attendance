package attendance.dto;

import attendance.domain.Attendance;

import java.time.LocalDateTime;

public record AttendanceResultResponse(
        LocalDateTime dateTime,
        String status
) {
    public static AttendanceResultResponse from(Attendance attendance) {
        return new AttendanceResultResponse(attendance.getDateTime(), attendance.getStatus().getMessage());
    }
}
