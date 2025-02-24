package util;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class DateTimeUtil {

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm", Locale.KOREAN);
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM월 dd일 E요일", Locale.KOREAN);
    public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm",
        Locale.KOREAN);

    private DateTimeUtil() {
    }

    public static LocalDate nowDate() {
        return LocalDate.now();
    }

    public static LocalTime convertToTime(String time) {
        try {
            return LocalTime.parse(time, TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("올바른 시간 형식이 아닙니다.");
        }
    }

    public static LocalDate convertToDate(LocalDate date, int day) {
        try {
            return date.withDayOfMonth(day);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("올바른 날짜(일)이 아닙니다.");
        }
    }
}
