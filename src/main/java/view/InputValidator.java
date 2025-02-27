package view;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InputValidator {
    private static final String TIME_FORMAT_ERROR = "[ERROR] 시간 형식(HH:mm)이 올바르지 않습니다.";
    private static final String VALID_TIME_FORMAT = "HH:mm";

    public static void validateTime(String time) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(VALID_TIME_FORMAT);
        try {
            LocalTime.parse(time, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(TIME_FORMAT_ERROR);
        }
    }

    public static void validateName(String invalidNameFormat) {

    }
}
