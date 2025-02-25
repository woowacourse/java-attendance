package dto;

import java.time.LocalTime;

public record CheckAttendanceResponse(
        LocalTime time,
        String attendanceStatus
) {
}