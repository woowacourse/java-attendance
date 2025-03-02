package util;

import common.SystemDate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeParser {


    private DateTimeParser() {
    }

    public static LocalDateTime parseToLocalDateTime(final String dateTime) {
        try {
            return LocalDateTime.parse(dateTime,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("[ERROR] 일시는 yyyy-MM-dd HH:mm 형식이어야 합니다.");
        }
    }

    public static LocalTime parseToLocalTime(final String time) {
        try {
            return LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("[ERROR] 시간은 HH:mm 형식으로 입력해주세요.");
        }
    }

    public static LocalDate parseToLocalDate(final String dayOfMonth) {
        try {
            return SystemDate.NOW.getDate().withDayOfMonth(Integer.parseInt(dayOfMonth));
        } catch (NumberFormatException | DateTimeParseException e) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 날짜 입니다.");
        }
    }
}
