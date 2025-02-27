package dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record CheckAttendanceRecordResponse(
        LocalDate date,
        LocalTime time,
        String attendanceStatus
) {
}