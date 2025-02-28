package service.dto;

import domain.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public record AttendanceRecordResponse(
        String date,
        String time,
        String attendanceStatus
) {

    private final static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM월 dd일 E요일", Locale.KOREAN);
    private final static DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    public static AttendanceRecordResponse from(LocalDate date) {
        return new AttendanceRecordResponse(date.format(DATE_FORMAT), "--:--", "결석");
    }

    public static AttendanceRecordResponse of(LocalDate date, LocalTime time, AttendanceStatus status) {
        return new AttendanceRecordResponse(
                date.format(DATE_FORMAT),
                time.format(TIME_FORMAT),
                status.getDescription()
        );
    }
}
