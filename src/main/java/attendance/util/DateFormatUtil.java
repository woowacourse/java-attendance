package attendance.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateFormatUtil {

    public static final DateTimeFormatter NOT_ATTENDABLE_FORMATTER = DateTimeFormatter.ofPattern(
            "MM월 dd일 EEEE은 등교일이 아닙니다.");
    public static final DateTimeFormatter MODIFY_ATTENDANCE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    public static final DateTimeFormatter CSV_DATE_TIME_FORMATER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private DateFormatUtil() {
        //인스턴스화 방지
    }

    public static LocalTime parseTime(String time) {
        try {
            return LocalTime.parse(time, MODIFY_ATTENDANCE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("잘못된 시간 형식입니다 - 올바른 형식: HH:mm)");
        }
    }

    public static LocalDateTime parseLocalDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, CSV_DATE_TIME_FORMATER);
    }

}
