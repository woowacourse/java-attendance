package dto;

import java.time.format.DateTimeFormatter;

public class Formatter {

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("MM월 dd일 %s요일 HH:mm");
}
