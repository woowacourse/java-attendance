package dto.result;

import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceModifyResult(
        LocalDate attendanceDate,
        LocalTime oldAttendanceTime,
        String oldAttendanceStatus,
        LocalTime newAttendanceTime,
        String newAttendanceStatus
) {
}
