package dto.result;

import domain.AttendanceStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record MemberAttendanceModifyResult(
        String name,
        LocalDate attendanceDate,
        LocalTime oldAttendanceTime,
        AttendanceStatus oldAttendanceStatus,
        LocalTime newAttendanceTime,
        AttendanceStatus newAttendanceStatus
) {
}
