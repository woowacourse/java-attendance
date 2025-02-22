package attendance.util;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Locale.KOREAN;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    private static final DateTimeFormatter YEAR_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = ofPattern("MM월 dd일 E요일 HH:mm", KOREAN);
    private static final DateTimeFormatter DATE_FORMATTER = ofPattern("MM월 dd일 E요일", KOREAN);
    private static final DateTimeFormatter TIME_FORMATTER = ofPattern("HH:mm", KOREAN);
    public static final String TIME_NOT_RECORDED = "--:--";

    private DateTimeUtil() {
    }

    public static LocalTime parseTime(String rawTime) {
        return LocalTime.parse(rawTime, TIME_FORMATTER);
    }

    public static LocalDateTime parseDateTime(String rawDateTime) {
        return LocalDateTime.parse(rawDateTime, YEAR_DATE_TIME_FORMATTER);
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DATE_TIME_FORMATTER);
    }

    public static String formatDate(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    public static String formatTime(LocalTime time) {
        return time.format(TIME_FORMATTER);
    }
}
