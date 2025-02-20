package attendance.domain.dto;

import java.time.LocalDateTime;

public record AttendanceModifyDto(String nickname, LocalDateTime attendanceDate) {
}
