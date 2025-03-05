package controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.Locale;

public class DateTimeConverter {
    private static final DateTimeFormatter FILE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter BASIC_DAY_FORMATTER = DateTimeFormatter.ofPattern("MM월 dd일");
    private static final DateTimeFormatter BASIC_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static LocalDateTime convertStringToLocalDateTime(String dateTime) {
        try {
            return LocalDateTime.parse(dateTime, FILE_FORMATTER);
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException("잘못된 날짜 형식을 입력하였습니다 [" + dateTime + "]");
        }
    }

    public static String convertLocalDateToString(LocalDate date) {
        try {
            return LocalDateTime.of(date, LocalTime.MIN).format(BASIC_DAY_FORMATTER) + " " +
                    date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);

        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException("잘못된 형식을 입력하였습니다 [" + date + "]");
        }
    }

    public static LocalDateTime convertStringToLocalTime(LocalDate date, String time) {
        try {
            return LocalDateTime.of(date, LocalTime.parse(time, BASIC_TIME_FORMATTER));
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException("잘못된 형식을 입력하였습니다");
        }
    }

    public static Object convertLocalTimeToString(LocalTime time) {
        try {
            return time.format(BASIC_TIME_FORMATTER);
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException("잘못된 형식을 입력하였습니다");
        }
    }
}
