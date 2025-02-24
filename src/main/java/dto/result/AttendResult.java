package dto.result;

import java.time.LocalDateTime;

public record AttendResult(
        LocalDateTime attendanceDateTime,
        String attendanceStatus,
        boolean triedAttend
) {
}
