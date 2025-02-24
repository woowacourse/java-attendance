package dto;

import java.time.LocalDate;
import java.time.LocalTime;

import domain.AttendanceStatus;

public record AttendanceResponseDto(
    LocalDate date,
    LocalTime time,
    AttendanceStatus status
) {

    public static AttendanceResponseDto of(LocalDate date, LocalTime time, AttendanceStatus status) {
        return new AttendanceResponseDto(date, time, status);
    }
}
