package attendance.domain.dto;

import java.time.LocalDateTime;

public record AttendanceDto(String nickname, LocalDateTime attendanceDate) {
}
