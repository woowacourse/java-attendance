package attendance.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import attendance.domain.AttendanceStatus;
import java.time.LocalTime;

public record AttendanceDateDto(LocalDate attendanceDate, LocalTime attendanceTime, AttendanceStatus attendanceStatus) {
}
