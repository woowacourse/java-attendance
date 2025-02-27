package attendance.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import attendance.domain.AttendanceStatus;

public record ModifyAttendanceResponse(
    LocalDate date,
    InnerAttendance before,
    InnerAttendance after
) {

    public record InnerAttendance(
        LocalTime time,
        AttendanceStatus status
    ) {

    }
}
