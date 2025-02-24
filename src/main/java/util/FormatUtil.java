package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormatUtil {

    public static final String ATTENDANCE_DATA_DELIMITER = ",";
    public static final int NICKNAME_INDEX = 0;
    public static final int DATE_TIME_INDEX = 1;
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final DateTimeFormatter DATE_FORMATTER_KOREAN = DateTimeFormatter.ofPattern("MM월 dd일");
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static LocalDateTime parseLocalDateTIme(String lowLocalDateTime) {
        return LocalDateTime.parse(
                lowLocalDateTime,
                FormatUtil.DATE_TIME_FORMATTER);
    }
}
