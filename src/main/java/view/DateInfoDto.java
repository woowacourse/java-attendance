package view;

import model.AttendanceDateTime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class DateInfoDto {

    private static final String KOREAN_DATE_PATTERN = "MM월 dd일 E요일";
    private static final String KOREAN_TIME_PATTERN = "HH:mm";
    private static final String KOREAN_DATE_TIME_PATTERN = KOREAN_DATE_PATTERN + " " + KOREAN_TIME_PATTERN;


    private final LocalDateTime dateTime;

    public DateInfoDto(final AttendanceDateTime attendanceDateTime) {
        this.dateTime = attendanceDateTime.getDateTime();
    }

    public static DateTimeFormatter onPattern(final String pattern) {
        return DateTimeFormatter.ofPattern(pattern, Locale.KOREAN);
    }

    public String getFormattedDateTime() {
        return dateTime.format(onPattern(KOREAN_DATE_TIME_PATTERN));
    }

    public String getFormattedDate() {
        return dateTime.format(onPattern(KOREAN_DATE_PATTERN));
    }

    public String getFormattedTime() {
        return dateTime.format(onPattern(KOREAN_TIME_PATTERN));
    }
}
