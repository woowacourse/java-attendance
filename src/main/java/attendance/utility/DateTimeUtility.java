package attendance.utility;

import attendance.exception.ExceptionMessage;
import attendance.exception.InputException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class DateTimeUtility {

    private static final DateTimeFormatter dateTimeformatter =
            DateTimeFormatter.ofPattern("MM월 dd일 E요일").withLocale(Locale.KOREA);

    public static LocalTime parseTimeByDefault(String input) {
        try {
            return LocalTime.parse(input);
        } catch (DateTimeParseException exception) {
            throw new InputException(ExceptionMessage.TIME_FORMAT_ERROR.getMessage());
        }
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTimeformatter.format(dateTime);
    }
}
