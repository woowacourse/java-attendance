package attendance.util;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import attendance.exception.ExceptionMessage;

public class DateTimeUtil {

    public static final DateTimeFormatter DATE_TIME_FORMATTER =
        DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm", Locale.KOREAN);
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM월 dd일 E요일", Locale.KOREAN);
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm", Locale.KOREAN);

    private DateTimeUtil() {
    }

    public static LocalDate nowDate() {
        return LocalDate.now();
    }

    public static LocalTime convertToLocalTime(String time) {
        try {
            return LocalTime.parse(time, DateTimeUtil.TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_TIME.getMessage(time));
        }
    }

    public static LocalDate convertToLocalDate(LocalDate date, int day) {
        try {
            return date.withDayOfMonth(day);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_DATE.getMessage(day));
        }
    }
}
