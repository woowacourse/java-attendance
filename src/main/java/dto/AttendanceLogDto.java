package dto;

import domain.attendance.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceLogDto(
        LocalDate attendanceDate,
        LocalTime attendanceTime,
        AttendanceStatus attendanceStatus
) {
}
