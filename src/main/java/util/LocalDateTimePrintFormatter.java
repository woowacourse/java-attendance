package util;

import constant.DateFormatInformation;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class LocalDateTimePrintFormatter {

    public static String LocalDateTimeToLocalTime(LocalDateTime localDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DateFormatInformation.LOCAL_TIME_FORMATTER);
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();

        if (localDateTime.format(dateTimeFormatter).equals("00:00")) {
            return localDateTime.format(DateTimeFormatter.ofPattern("MM월 dd일 " + dayOfWeek.getDisplayName(
                    TextStyle.FULL, Locale.KOREAN) + " --:--"));
        }

        return localDateTime.format(DateTimeFormatter.ofPattern("MM월 dd일 " + dayOfWeek.getDisplayName(
                TextStyle.FULL, Locale.KOREAN) + DateFormatInformation.LOCAL_DATE_TIME_FORMATTER));
    }
}
