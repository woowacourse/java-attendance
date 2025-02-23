package dto.result;

import domain.AttendanceStatus;

import java.time.LocalDateTime;

public record AttendResult(
        LocalDateTime attendanceDateTime,
        AttendanceStatus attendanceStatus,
        boolean triedAttend
) {
}
