package common;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateTimeFormat {
    public static final DateTimeFormatter MONTH_DATE_DAY_FORMATTER = DateTimeFormatter.ofPattern(
            "MM월 dd일 E요일")
            .withLocale(Locale.forLanguageTag("ko"));

    public static final DateTimeFormatter HOUR_MINUTE_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static final DateTimeFormatter YEAR_MONTH_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
}
