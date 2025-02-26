package util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class Parser {
    public static String parseDateInKorean(LocalDate date) {
        return date.getMonthValue() + "월 " + date.getDayOfMonth() + "일 " + parseDayOfWeekInKorean(date.getDayOfWeek());
    }

    private static String parseDayOfWeekInKorean(DayOfWeek dayOfWeek) {
        return dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN);
    }

    public static String parseTimeToString(LocalTime time) {
        return time.format(DateTimeFormatter.ofPattern("hh:mm"));
    }
}

