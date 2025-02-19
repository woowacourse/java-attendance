package dto;

import java.time.LocalDateTime;

public record AttendanceResultDTO(
        LocalDateTime attendanceTime,
        String attendanceStatus
) {
}
