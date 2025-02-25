package dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record CheckAttendanceResponse(LocalDate date, LocalTime time, String status) {
}
