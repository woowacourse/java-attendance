package util.parser;

import static util.constant.ErrorMessage.DATE_TIME_FORMAT_ERROR_MESSAGE;

import java.time.DateTimeException;
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
        try {
            return LocalDateTime.parse(dateTime, dateTimeFormatter);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException(DATE_TIME_FORMAT_ERROR_MESSAGE);
        }
    }

    public static LocalTime parseStringToTime(String time) {
        try {
            return LocalTime.parse(time, timeFormatter);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException(DATE_TIME_FORMAT_ERROR_MESSAGE);
        }
    }

    public static LocalDate parseIntegerToDate(int year, int month, int day) {
        try {
            return LocalDate.of(year, month, day);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException(DATE_TIME_FORMAT_ERROR_MESSAGE);
        }
    }

    public static LocalTime parseIntegerToTime(int hour, int minute) {
        try {
            return LocalTime.of(hour, minute);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException(DATE_TIME_FORMAT_ERROR_MESSAGE);
        }
    }
}
