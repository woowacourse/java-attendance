package util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import presentation.InputValidator;

public class DateTimeUtil {
    private final static DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("MM월 dd일");
    private final static DateTimeFormatter localTimeFormatter = DateTimeFormatter.ofPattern("hh:mm");
    private final static DateTimeFormatter localTimeKoreanFormatter = DateTimeFormatter.ofPattern("hh시 mm분");

    public static String convertLocalDateTimeToString(LocalDateTime localDateTime) {
        try {
            String parsedLocalDate = localDateTime.format(localDateFormatter);
            String parsedLocalTime = localDateTime.format(localTimeKoreanFormatter);
            return parsedLocalDate + " " + convertDayOfWeekToString(localDateTime.getDayOfWeek().getValue()) + " " +
                    parsedLocalTime;
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException(InputValidator.INVALID_DATETIME_ARGUMENT_EXCEPTION);
        }
    }

    public static String convertLocalDateToString(LocalDate localDate) {
        try {
            String parsedLocalDate = localDate.format(localDateFormatter);
            return parsedLocalDate + " " + convertDayOfWeekToString(localDate.getDayOfWeek().getValue());
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

    private static String convertDayOfWeekToString(int dayOfWeek) {
        if (dayOfWeek == 1) {
            return "월요일";
        }
        if (dayOfWeek == 2) {
            return "화요일";
        }
        if (dayOfWeek == 3) {
            return "수요일";
        }
        if (dayOfWeek == 4) {
            return "목요일";
        }
        if (dayOfWeek == 5) {
            return "금요일";
        }
        if (dayOfWeek == 6) {
            return "토요일";
        }
        return "일요일";
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
