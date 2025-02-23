package dto.result;

import domain.AttendanceStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceModifyResult(
        LocalDate attendanceDate,
        LocalTime oldAttendanceTime,
        AttendanceStatus oldAttendanceStatus,
        LocalTime newAttendanceTime,
        AttendanceStatus newAttendanceStatus
) {
}
