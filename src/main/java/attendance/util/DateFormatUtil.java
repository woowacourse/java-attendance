package attendance.util;

import static attendance.error.ErrorMessage.ERROR_INVALID_TIME;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateFormatUtil {
    public final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM월 dd일 EEEE");
    public final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM월 dd일 EEEE HH:mm");
    public final static DateTimeFormatter NO_ATTENDANCE_DATE_FORMATTER = DateTimeFormatter.ofPattern(
            "MM월 dd일 EEEE --:--");

    public static LocalTime parseToTime(String str) {
        try {
            return LocalTime.parse(str, DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(ERROR_INVALID_TIME);
        }
    }
}
