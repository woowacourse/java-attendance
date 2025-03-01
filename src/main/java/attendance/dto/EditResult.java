package attendance.dto;

import attendance.model.AttendanceType;
import java.time.LocalDate;
import java.time.LocalTime;

public record EditResult(
        LocalDate targetDate,
        LocalTime beforeAttendanceTime,
        AttendanceType beforeAttendanceType,
        LocalTime afterAttendanceTime,
        AttendanceType afterAttendanceType
) {
}
