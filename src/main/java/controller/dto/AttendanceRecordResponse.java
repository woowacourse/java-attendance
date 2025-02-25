package controller.dto;

import domain.AttendanceRecord;
import java.time.LocalDate;
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

    public static AttendanceRecordResponse from(AttendanceRecord attendanceRecord) {
        return new AttendanceRecordResponse(attendanceRecord.date(), attendanceRecord.time().format(TIME_FORMATTER),
                attendanceRecord.status().getTitle());
    }
}
