package util;

import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
    public static final DateTimeFormatter localDayFormatter = DateTimeFormatter.ofPattern("MM월 dd일 ");
    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    public static final DateTimeFormatter dateTimeInputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
}
