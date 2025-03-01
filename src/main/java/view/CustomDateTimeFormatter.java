package view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class CustomDateTimeFormatter {
    private final static String DATE_FORMAT = "MM월 dd일";
    private final static String TIME_FORMAT = "HH:mm";

    private final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private final static DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);


    public static String dateToString(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    public static String dateToDayOfWeek(LocalDate date) {
        return date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
    }

    public static String timeToString(LocalTime time) {
        return time.format(TIME_FORMATTER);
    }
}
