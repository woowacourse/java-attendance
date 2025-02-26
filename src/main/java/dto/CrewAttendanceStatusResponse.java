package dto;

import domain.AttendanceStatus;
import java.time.LocalTime;

public record CrewAttendanceStatusResponse(
        LocalTime time,
        AttendanceStatus attendanceStatus
) {
}
