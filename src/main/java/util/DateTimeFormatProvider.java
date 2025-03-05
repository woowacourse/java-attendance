package util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class DateTimeFormatProvider {

    private static final DateTimeFormatter FORMATTER_1 = DateTimeFormatter.ofPattern("MM월 dd일 E요일", Locale.KOREAN);
    private static final DateTimeFormatter FORMATTER_2 = DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm",
            Locale.KOREAN);
    private static final DateTimeFormatter FORMATTER_3 = DateTimeFormatter.ofPattern("HH:mm");

    private DateTimeFormatProvider() {
    }

    public static String toLocalDateKoreanFormat(final LocalDate localDate) {
        return localDate.format(FORMATTER_1);
    }

    public static String toDayOfWeekKoreanFormat(final DayOfWeek dayOfWeek) {
        return dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN);
    }

    public static String toLocalDateTimeKoreanFormat(final LocalDateTime localDateTime) {
        return localDateTime.format(FORMATTER_2);
    }

    public static String toLocalTimeKoreanFormat(final LocalTime localTime) {
        return localTime.format(FORMATTER_3);
    }
}
