package dto;

import domain.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalTime;

public record ModifyAttendanceResponse(
        LocalDate date,
        LocalTime originalTime,
        LocalTime modifiedTime,
        AttendanceStatus originalStatus,
        AttendanceStatus modifiedStatus
) {
}
