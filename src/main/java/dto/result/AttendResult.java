package dto.result;

import java.time.LocalDateTime;

public record AttendResult(
        LocalDateTime attendanceTime,
        String attendanceStatus
) {
}
