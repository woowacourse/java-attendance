package dto;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Formatter {

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm", Locale.KOREAN);
    public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm",
        Locale.KOREAN);
}
