package util;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class DayOfWeekConverter {
    public static String convertDayOfWeek(LocalDateTime dateTime) {
        return dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
    }

    public static String convertDayOfWeek(int date, LocalDateTime dateTime) {
        LocalDateTime changeDateTime = LocalDateTime.of(dateTime.getYear(), dateTime.getMonth().getValue(), date,
                dateTime.getHour(), dateTime.getMinute());

        return convertDayOfWeek(changeDateTime);
    }

    
}
