package view;

import model.AttendanceDateTime;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class DateInfoDto {

    private static final String KOREAN_DATE_PATTERN = "MM월 dd일 E요일";
    private static final String BASIC_TIME_PATTERN = "HH:mm";
    private static final String ABSENCE_TIME_PATTERN = "--:--";

    private final LocalDateTime dateTime;

    public DateInfoDto(final AttendanceDateTime attendanceDateTime) {
        this.dateTime = attendanceDateTime.getDateTime();
    }

    public static DateTimeFormatter onPattern(final String pattern) {
        return DateTimeFormatter.ofPattern(pattern, Locale.KOREAN);
    }

    public String getFormattedDateTime() {
        final String timePattern = findTimeFormatter(dateTime);
        final String formattedDateTimePattern = KOREAN_DATE_PATTERN + " " + timePattern;
        return dateTime.format(onPattern(formattedDateTimePattern));
    }

    public String getFormattedDate() {
        return dateTime.format(onPattern(KOREAN_DATE_PATTERN));
    }

    public String getFormattedTime() {
        return dateTime.format(onPattern(findTimeFormatter(dateTime)));
    }

    private String findTimeFormatter(final LocalDateTime dateTime) {
        if (dateTime.toLocalTime().equals(LocalTime.of(0, 0))) {
            return ABSENCE_TIME_PATTERN;
        }
        return BASIC_TIME_PATTERN;
    }
}

