package util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class DateFormatter {

    public final static int YEAR = 2024;
    public final static int MONTH = 12;
    private final static String DATE_FORMAT = "M월 d일 EEEE";
    private final static String TIME_FORMAT = "HH:mm";
    private final static String DATE_TIME_FORMAT = "M월 d일 EEEE HH:mm";
    private final static String DATE_TIME_INPUT_FORMAT = "yyyy-MM-dd HH:mm";
    private final static String DEFAULT_TIME = "--:--";


    public static String dateFullFormat(LocalDate localDate, LocalTime localTime) {
        String date = dateFormat(localDate);
        String time = DEFAULT_TIME;
        if (localTime != null) {
            time = timeFormat(localTime);
        }
        return date + " " + time;
    }

    public static String dateFullFormat(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        return formatter.format(localDateTime);
    }

    public static String dateFormat(LocalDate localDate) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return dateFormatter.format(localDate);
    }

    public static String timeFormat(LocalTime localTime) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
        return timeFormatter.format(localTime);
    }

    public static LocalTime changeInputToTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
        return LocalTime.parse(time, formatter);
    }

    public static LocalDateTime changeInputToDateTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_INPUT_FORMAT);
        return LocalDateTime.parse(time, formatter);
    }

    public static LocalDate getTodayDate() {
        LocalDate now = LocalDate.now();
        return LocalDate.of(YEAR, MONTH, now.getDayOfMonth());
    }
}
