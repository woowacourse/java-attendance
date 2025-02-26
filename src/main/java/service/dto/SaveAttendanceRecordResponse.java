package service.dto;

import domain.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record SaveAttendanceRecordResponse(
        LocalDateTime dateTime,
        AttendanceStatus status
) {

    public static SaveAttendanceRecordResponse of(LocalDate date, LocalTime time, AttendanceStatus status) {
        return new SaveAttendanceRecordResponse(LocalDateTime.of(date, time), status);
    }
}
