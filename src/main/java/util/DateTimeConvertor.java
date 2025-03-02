package util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class DateTimeConvertor {

    private static final DateTimeFormatter FORMATTER_1 = DateTimeFormatter.ofPattern("MM월 dd일 E요일", Locale.KOREAN);
    private static final DateTimeFormatter FORMATTER_2 = DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm", Locale.KOREAN);
    private static final DateTimeFormatter FORMATTER_3 = DateTimeFormatter.ofPattern("HH:mm");

    private DateTimeConvertor() {
    }

    public static String convertToLocalDateKoreanFormat(final LocalDate localDate) {
        return localDate.format(FORMATTER_1);
    }

    public static String convertToDayOfWeekKoreanFormat(final DayOfWeek dayOfWeek) {
        return dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN);
    }

    public static String convertToLocalDateTimeKoreanFormat(final LocalDateTime localDateTime) {
        return localDateTime.format(FORMATTER_2);
    }

    public static String convertToLocalTimeKoreanFormat(final LocalTime localTime) {
        return localTime.format(FORMATTER_3);
    }
}
