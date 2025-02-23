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
    private final static DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("MM월 dd일");
    private final static DateTimeFormatter localTimeFormatter = DateTimeFormatter.ofPattern("hh:mm");
    private final static DateTimeFormatter localTimeKoreanFormatter = DateTimeFormatter.ofPattern("hh시 mm분");

    public static String convertLocalDateTimeToString(LocalDateTime localDateTime) {
        try {
            String parsedLocalDate = localDateTime.format(localDateFormatter);
            String parsedLocalTime = localDateTime.format(localTimeKoreanFormatter);
            String parsedDayOfWeek = convertDayOfWeekToString(localDateTime.getDayOfWeek());
            return parsedLocalDate + " " + parsedDayOfWeek + " " +
                    parsedLocalTime;
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException(InputValidator.INVALID_DATETIME_ARGUMENT_EXCEPTION);
        }
    }

    public static String convertLocalDateToString(LocalDate localDate) {
        try {
            String parsedLocalDate = localDate.format(localDateFormatter);
            String parsedDayOfWeek = convertDayOfWeekToString(localDate.getDayOfWeek());
            return parsedLocalDate + " " + parsedDayOfWeek;
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException(InputValidator.INVALID_DATETIME_ARGUMENT_EXCEPTION);
        }
    }

    public static String convertLocalDateTimeToTimeString(LocalDateTime localDateTime) {
        try {
            return localDateTime.format(localTimeFormatter);
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException(InputValidator.INVALID_DATETIME_ARGUMENT_EXCEPTION);
        }
    }

    private static String convertDayOfWeekToString(DayOfWeek dayOfWeek) {
        return dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN);
    }

    public static LocalDateTime convertStringToLocalDateTime(LocalDate localDate, String textLocalTime) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime localTime = LocalTime.parse(textLocalTime, formatter);
            return LocalDateTime.of(localDate, localTime);
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException(InputValidator.INVALID_DATETIME_ARGUMENT_EXCEPTION);
        }
    }

    public static LocalDateTime convertStringToLocalDateTime(String textLocalDateTime) {
        try {
            DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime localTime = LocalDateTime.parse(textLocalDateTime, localDateTimeFormatter);
            return localTime;
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException(InputValidator.INVALID_DATETIME_ARGUMENT_EXCEPTION);
        }
    }
}
