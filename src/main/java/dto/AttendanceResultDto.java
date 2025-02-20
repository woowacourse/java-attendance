package dto;

import java.time.LocalDateTime;

public record AttendanceResultDto(LocalDateTime localDateTime, String attendanceState) {
}
