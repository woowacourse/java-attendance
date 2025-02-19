package util;

import java.time.LocalDateTime;

public class DateTimeUtil {
    private final static DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("MM월 dd일");
    private final static DateTimeFormatter localTimeFormatter = DateTimeFormatter.ofPattern("hh시 mm분");

    public static int getDayOfWeek(LocalDateTime localDateTime) {
        return localDateTime.getDayOfWeek().getValue();
    }
}
