package attendance.utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateConverter {

    public static final DateTimeFormatter LOCAL_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private DateConverter() {}

    public static LocalDate convertToLocalDate(String dateAndTime) {
        try {
            return LocalDate.parse(dateAndTime, LOCAL_DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("[ERROR] 날짜 입력 형식이 올바르지 않습니다.");
        }
    }

    public static LocalTime convertToLocalTime(String dateAndTime) {
        try {
            return LocalTime.parse(dateAndTime, LOCAL_DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("[ERROR] 날짜 입력 형식이 올바르지 않습니다.");
        }
    }

    public static String convertToString(LocalDate attendanceDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 d일");
        return attendanceDate.format(formatter);
    }
}
