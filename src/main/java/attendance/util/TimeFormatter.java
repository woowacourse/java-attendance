package attendance.util;

import static attendance.domain.model.AttendanceStatus.DEFAULT_TIME;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TimeFormatter {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM월 dd일 E요일")
            .withLocale(Locale.KOREAN);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final String DEFAULT_TIME_FORMAT = "--:--";

    public static String formatDateTime(final LocalDateTime localDateTime) {
        LocalDate localDate = LocalDate.from(localDateTime);
        LocalTime localTime = LocalTime.from(localDateTime);
        return formatDate(localDate) + " " + formatTime(localTime);
    }

    public static String formatDate(final LocalDate localDate) {
        return localDate.format(DATE_FORMATTER);
    }

    public static String formatTime(final LocalTime localTime) {
        if (DEFAULT_TIME.equals(localTime)) {
            return DEFAULT_TIME_FORMAT;
        }
        return localTime.format(TIME_FORMATTER);
    }
}
