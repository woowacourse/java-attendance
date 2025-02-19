package dto;

import java.time.LocalDate;
import java.time.LocalTime;

import domain.AttendanceStatus;

public record AttendanceResult(
    LocalDate date,
    LocalTime time,
    AttendanceStatus status
) {
    public static AttendanceResult of(LocalDate date, LocalTime time, AttendanceStatus status) {
        return new AttendanceResult(date, time, status);
    }
}
