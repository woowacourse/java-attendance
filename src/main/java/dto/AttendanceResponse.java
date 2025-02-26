package dto;

import domain.AttendanceStatus;
import java.time.LocalDateTime;

public record AttendanceResponse(LocalDateTime attendanceDate, AttendanceStatus attendanceStatus, boolean isEmpty) {
}
