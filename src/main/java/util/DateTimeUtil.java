package util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.Locale;
import presentation.InputValidator;

public class DateTimeUtil {
    private static final DateTimeFormatter LOCAL_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter LOCAL_DATE_FORMATTER = DateTimeFormatter.ofPattern("MM월 dd일");
    private static final DateTimeFormatter LOCAL_TIME_KOREAN_FORMATTER = DateTimeFormatter.ofPattern("hh시 mm분");
    private static final DateTimeFormatter LOCAL_TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mm");

    public static String convertLocalDateTimeToString(LocalDateTime localDateTime) {
        try {
            String parsedLocalDate = localDateTime.format(LOCAL_DATE_FORMATTER);
            String parsedLocalTime = localDateTime.format(LOCAL_TIME_KOREAN_FORMATTER);
            String parsedDayOfWeek = convertDayOfWeekToString(localDateTime.getDayOfWeek());
            return parsedLocalDate + " " + parsedDayOfWeek + " " +
                    parsedLocalTime;
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException(InputValidator.INVALID_DATETIME_ARGUMENT_EXCEPTION);
        }
    }

    public static String convertLocalDateToString(LocalDate localDate) {
        try {
            String parsedLocalDate = localDate.format(LOCAL_DATE_FORMATTER);
            String parsedDayOfWeek = convertDayOfWeekToString(localDate.getDayOfWeek());
            return parsedLocalDate + " " + parsedDayOfWeek;
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException(InputValidator.INVALID_DATETIME_ARGUMENT_EXCEPTION);
        }
    }

    public static String convertLocalDateTimeToTimeString(LocalDateTime localDateTime) {
        try {
            return localDateTime.format(LOCAL_TIME_FORMATTER);
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException(InputValidator.INVALID_DATETIME_ARGUMENT_EXCEPTION);
        }
    }

    private static String convertDayOfWeekToString(DayOfWeek dayOfWeek) {
        return dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN);
    }

    public static LocalDateTime convertStringToLocalDateTime(LocalDate localDate, String textLocalTime) {
        try {
            LocalTime localTime = LocalTime.parse(textLocalTime, LOCAL_TIME_FORMATTER);
            return LocalDateTime.of(localDate, localTime);
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException(InputValidator.INVALID_DATETIME_ARGUMENT_EXCEPTION);
        }
    }

    public static LocalDateTime convertStringToLocalDateTime(String textLocalDateTime) {
        try {
            LocalDateTime localTime = LocalDateTime.parse(textLocalDateTime, LOCAL_DATE_TIME_FORMATTER);
            return localTime;
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException(InputValidator.INVALID_DATETIME_ARGUMENT_EXCEPTION);
        }
    }
}
