package util.parser;

import static util.constant.Value.DATE_FORMAT;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeParser {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    private DateTimeParser() {
    }

    public static LocalDateTime parseStringToDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, dateTimeFormatter);
    }

    public static LocalTime parseStringToTime(String time) {
        return LocalTime.parse(time, timeFormatter);
    }

    public static LocalDate parseIntegerToDate(int year, int month, int day) {
        return LocalDate.of(year, month, day);
    }

}
