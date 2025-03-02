package util;

import domain.ErrorCode;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class InputParser {
    public static LocalTime parseToLocalTime(String input) {
        try {
            return LocalTime.parse(input);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(ErrorCode.INPUT_TIME_NOT_VALID.getMessage());
        }
    }
}
