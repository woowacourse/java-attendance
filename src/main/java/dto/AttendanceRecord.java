package dto;

import java.time.LocalDate;

public record AttendanceRecord(
        LocalDate date,
        Time time) {
}
