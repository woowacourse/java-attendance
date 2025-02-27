package dto;

import java.time.LocalDateTime;

public record AttendanceRecordDto(
        LocalDateTime dateTime,
        boolean isEmpty,
        String attendanceStatus
) {
}
