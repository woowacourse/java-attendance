package dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceModifyDTO(
        LocalDate attendanceDate,
        LocalTime oldAttendanceTime,
        String oldAttendanceStatus,
        LocalTime newAttendanceTime,
        String newAttendanceStatus
) {
}
