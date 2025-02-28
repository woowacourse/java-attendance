package util.parser;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeParser {

    private static final String PARSE_DATE_TIME_ERROR_MESSAGE = "잘못된 날짜 및 시간 입력입니다.";

    private DateTimeParser() {
    }

    public static LocalDateTime parseStringToDateTime(String dateTime) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return LocalDateTime.parse(dateTime, formatter);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException(PARSE_DATE_TIME_ERROR_MESSAGE);
        }
    }

    public static LocalDate parseStringToDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(date, formatter);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException(PARSE_DATE_TIME_ERROR_MESSAGE);
        }
    }

    public static LocalTime parseStringToTime(String time) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            return LocalTime.parse(time, formatter);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException(PARSE_DATE_TIME_ERROR_MESSAGE);
        }
    }

    public static LocalDate parseIntegerToDate(int year, int month, int day) {
        try {
            return LocalDate.of(year, month, day);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException(PARSE_DATE_TIME_ERROR_MESSAGE);
        }
    }

    public static LocalTime parseIntegerToTime(int hour, int minute) {
        try {
            return LocalTime.of(hour, minute);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException(PARSE_DATE_TIME_ERROR_MESSAGE);
        }
    }
}