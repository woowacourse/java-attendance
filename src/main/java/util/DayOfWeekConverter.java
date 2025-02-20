package util;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class DayOfWeekConverter {
    public static String convertDayOfWeek(LocalDateTime dateTime) {
        return dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
    }
}
