package service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public record SaveAttendanceRecordResponse(
        String dateTime,
        String status
) {

    private static final DateTimeFormatter DATE_TIME_FORMAT
            = DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm", Locale.KOREAN);

    public static SaveAttendanceRecordResponse of(LocalDate date, LocalTime time, String status) {
        return new SaveAttendanceRecordResponse(LocalDateTime.of(date, time).format(DATE_TIME_FORMAT), status);
    }
}
