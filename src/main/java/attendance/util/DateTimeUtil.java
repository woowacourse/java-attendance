package attendance.util;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class DateTimeUtil {

    public static final DateTimeFormatter DATE_TIME_FORMATTER =
        DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm", Locale.KOREAN);
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM월 dd일 E요일");
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private DateTimeUtil() {
    }

    public static LocalDate nowDate() {
        return LocalDate.now();
    }

    public static LocalTime convertToLocalTime(String time) {
        try {
            return LocalTime.parse(time, DateTimeUtil.TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(String.format("{%s}는 잘못된 시간입니다.", time));
        }
    }

    public static LocalDate convertToLocalDate(LocalDate date, int day) {
        try {
            return date.withDayOfMonth(day);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException(String.format("{%d}는 잘못된 날짜입니다.", day));
        }
    }
}
