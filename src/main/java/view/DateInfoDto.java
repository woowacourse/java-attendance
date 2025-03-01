package view;

import model.AttendanceDateTime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class DateInfoDto {

    private static final DateTimeFormatter KOREAN_DATE_FORMAT = DateTimeFormatter.ofPattern("MM월 dd일 E요일", Locale.KOREAN);
    private static final DateTimeFormatter KOREAN_TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm", Locale.KOREAN);

    private final LocalDateTime dateTime;

    public DateInfoDto(final AttendanceDateTime attendanceDateTime) {
        this.dateTime = attendanceDateTime.getDateTime();
    }

    public String getFormattedDate() {
        return dateTime.format(KOREAN_DATE_FORMAT);
    }

    public String getFormattedTime() {
        return dateTime.format(KOREAN_TIME_FORMAT);
    }
}
