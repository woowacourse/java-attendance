package dto.result;

import java.time.LocalDate;
import java.time.LocalTime;

public record MemberAttendanceModifyResult(
        String name,
        LocalDate attendanceDate,
        LocalTime oldAttendanceTime,
        String oldAttendanceStatus,
        LocalTime newAttendanceTime,
        String newAttendanceStatus
) {
}
