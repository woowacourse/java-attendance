package controller.dto;

import domain.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record SavedAttendanceRecord(
        LocalDateTime dateTime,
        AttendanceStatus status
) {

    public static SavedAttendanceRecord of(LocalDate date, LocalTime time, AttendanceStatus status) {
        return new SavedAttendanceRecord(LocalDateTime.of(date, time), status);
    }
}
