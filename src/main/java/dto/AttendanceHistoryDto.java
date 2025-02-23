package dto;

import java.time.LocalDateTime;

public record AttendanceHistoryDto(LocalDateTime localDateTime, String attendanceState) {
}
