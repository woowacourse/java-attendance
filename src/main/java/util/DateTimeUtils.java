package util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
    public static final int NOW_YEAR = 2024;
    public static final int NOW_MONTH = 12;
    public static final int NOW_DAY = 13;
    public static final int NOW_HOUR = 15;
    public static final int NOW_MINUTE = 13;

    public static final LocalDateTime TODAY_DATE_TIME_NOW = LocalDateTime.of(NOW_YEAR, NOW_MONTH, NOW_DAY, NOW_HOUR, NOW_MINUTE);
    public static final LocalDate TODAY_DATE_NOW = LocalDate.of(NOW_YEAR, NOW_MONTH, NOW_DAY);


    public static final DateTimeFormatter localDayFormatter = DateTimeFormatter.ofPattern("MM월 dd일 ");
    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    public static final DateTimeFormatter dateTimeInputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
}
