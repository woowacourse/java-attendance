package service.dto;

import java.time.LocalDateTime;

public record AttendanceModifyResponse(
        LocalDateTime beforeTime,
        String beforeStatus,
        LocalDateTime afterTime,
        String afterStatus
) {
}
