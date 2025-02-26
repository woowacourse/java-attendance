package dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ModifyAttendanceResponse(
        LocalDate date,
        LocalTime time,
        String attendanceStatus
) {
}