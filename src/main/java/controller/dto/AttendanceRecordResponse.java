package controller.dto;

import domain.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public record AttendanceRecordResponse(
        LocalDate date,
        String time,
        String attendanceStatus
) {

    private final static DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static AttendanceRecordResponse from(LocalDate date) {
        return new AttendanceRecordResponse(date, "--:--", "결석");
    }

    public static AttendanceRecordResponse of(LocalDate date, LocalTime time, AttendanceStatus status) {
        return new AttendanceRecordResponse(date, time.format(TIME_FORMATTER), status.getTitle());
    }
}
