package dto;

import domain.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceRecordResponse(
        LocalDate date,
        LocalTime time,
        AttendanceStatus attendanceStatus
) {
}