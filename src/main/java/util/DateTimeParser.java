package util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateTimeParser {


    private static final DateTimeFormatter FORMATTER_1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter FORMATTER_2 = DateTimeFormatter.ofPattern("M월 d일 E요일", Locale.KOREAN);

    private DateTimeParser() {
    }

    public static LocalDateTime parseToLocalDateTime(final String dateTime) {
        return LocalDateTime.parse(dateTime, FORMATTER_1);
    }

    public static String parseToLocalDateKoreanFormat(final LocalDate localDate) {
        return localDate.format(FORMATTER_2);
    }
}
