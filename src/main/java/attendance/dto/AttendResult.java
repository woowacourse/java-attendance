package attendance.dto;

import attendance.model.AttendanceType;
import java.time.LocalDateTime;

public record AttendResult(
        LocalDateTime attendanceDateTime,
        AttendanceType attendanceType
) {
}
