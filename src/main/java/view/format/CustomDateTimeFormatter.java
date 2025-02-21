package view.format;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CustomDateTimeFormatter {
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM월 dd일 E요일")
            .withLocale(Locale.forLanguageTag("ko"));

    public static LocalTime parseTime(String input) {
        return LocalTime.parse(input, timeFormatter);
    }

    public static LocalDateTime parseFullDateAndTime(String input) {
        return LocalDateTime.parse(input, fullDateTimeFormatter);
    }

    public static String formatTime(LocalDateTime time) {
        return time.format(timeFormatter);
    }

    public static String formatDateAndDay(LocalDateTime time) {
        return time.format(dateFormatter);
    }
}
