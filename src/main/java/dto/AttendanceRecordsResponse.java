package dto;

import domain.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceRecordsResponse(LocalDate date, LocalTime time, AttendanceStatus attendanceStatus) {
}