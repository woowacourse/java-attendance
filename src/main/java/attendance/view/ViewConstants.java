package attendance.view;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ViewConstants {
    static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("YYYY년 M월 d일 E요일").withLocale(Locale.KOREA);
    static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
}
