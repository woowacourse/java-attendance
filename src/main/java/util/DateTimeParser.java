package util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class DateTimeParser {


    private static final DateTimeFormatter FORMATTER_1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter FORMATTER_2 = DateTimeFormatter.ofPattern("MM월 dd일 E요일", Locale.KOREAN);
    private static final DateTimeFormatter FORMATTER_3 = DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm", Locale.KOREAN);
    private static final DateTimeFormatter FORMATTER_4 = DateTimeFormatter.ofPattern("HH:mm");

    private DateTimeParser() {
    }

    public static LocalDateTime parseToLocalDateTime(final String dateTime) {
        return LocalDateTime.parse(dateTime, FORMATTER_1);
    }

    public static String parseToLocalDateKoreanFormat(final LocalDate localDate) {
        return localDate.format(FORMATTER_2);
    }

    public static String parseToDayOfWeekKoreanFormat(final DayOfWeek dayOfWeek) {
        return dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN);
    }

    public static String parseToLocalDateTimeKoreanFormat(final LocalDateTime localDateTime) {
        return localDateTime.format(FORMATTER_3);
    }

    public static String parseToLocalTimeKoreanFormat(final LocalTime localTime) {
        return localTime.format(FORMATTER_4);
    }
}
