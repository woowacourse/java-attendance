package view;

import static util.Constants.ERROR_HEADER;
import static util.Constants.TIME_FORMAT;
import static util.Constants.TODAY;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InputValidator {
    private static final String EMPTY_INPUT_ERROR = "입력된 것이 없습니다.";
    private static final String TIME_FORMAT_ERROR = "시간 형식이 올바르지 않습니다.";
    private static final String INVALID_DATE_ERROR = "날짜 형식이 올바르지 않습니다.";

    public static void validateNotEmpty(String input) {
        if(input.isBlank()) {
            throw new IllegalArgumentException(ERROR_HEADER + EMPTY_INPUT_ERROR);
        }
    }

    public static void validateTime(String time) {
        validateNotEmpty(time);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
        try {
            LocalTime.parse(time, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(ERROR_HEADER + TIME_FORMAT_ERROR);
        }
    }

    public static void validateDay(String day) {
        validateNotEmpty(day);
        try {
            int dayNumber = Integer.parseInt(day);
            validateNotFuture(dayNumber);
            LocalDate.of(TODAY.getYear(), TODAY.getMonth(), dayNumber);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException(ERROR_HEADER + INVALID_DATE_ERROR);
        }
    }

    private static void validateNotFuture(int day) {
        if(day > TODAY.getDayOfMonth()) {
            throw new IllegalArgumentException(ERROR_HEADER + INVALID_DATE_ERROR);
        }
    }
}
