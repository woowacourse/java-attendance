package util.parser;

import static java.util.Locale.KOREAN;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;

public class OutputParser {
    private static final String TIME_NULL_DISPLAY = "--:--";

    public static String parseDateInKorean(LocalDate date) {
        return date.getMonthValue() + "월 " + date.getDayOfMonth() + "일 " + parseDateToDayOfWeek(date);
    }

    public static String parseDateToDayOfWeek(LocalDate date) {
        return parseDayOfWeekInKorean(date.getDayOfWeek());
    }

    private static String parseDayOfWeekInKorean(DayOfWeek dayOfWeek) {
        return dayOfWeek.getDisplayName(TextStyle.FULL, KOREAN);
    }

    public static String parseTimeToString(LocalTime time) {
        if (time == null) {
            return TIME_NULL_DISPLAY;
        }
        return time.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
