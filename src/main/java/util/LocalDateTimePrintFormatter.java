package util;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class LocalDateTimePrintFormatter {

    public static final DateTimeFormatter dateTimeFormatterForHourMin = DateTimeFormatter.ofPattern("HH:mm");

    public static String LocalDateTimeToLocalTime(LocalDateTime localDateTime) {
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();

        if (localDateTime.format(dateTimeFormatterForHourMin).equals("00:00")) {
            return localDateTime.format(DateTimeFormatter.ofPattern("MM월 dd일 " + dayOfWeek.getDisplayName(
                    TextStyle.FULL, Locale.KOREAN) + " --:--"));
        }

        return localDateTime.format(DateTimeFormatter.ofPattern("MM월 dd일 " + dayOfWeek.getDisplayName(
                TextStyle.FULL, Locale.KOREAN) + " HH:mm"));
    }

}
