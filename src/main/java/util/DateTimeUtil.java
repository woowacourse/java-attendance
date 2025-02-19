package util;

import java.time.LocalDateTime;

public class DateTimeUtil {
    public static int getDayOfWeek(LocalDateTime localDateTime) {
        return localDateTime.getDayOfWeek().getValue();
    }
}
