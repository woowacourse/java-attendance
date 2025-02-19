package dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceModifyResult(
        String name,
        LocalDate attendanceDate,
        LocalTime oldAttendanceTime,
        String oldAttendanceStatus,
        LocalTime newAttendanceTime,
        String newAttendanceStatus
) {
}
