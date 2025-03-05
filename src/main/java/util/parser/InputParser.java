package util.parser;

import domain.ErrorCode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class InputParser {
    public static LocalDate parseToDecemberLocalDate(String input) {
        try {
            return LocalDate.of(2024, 12, Integer.parseInt(input));
        } catch (DateTimeParseException | NumberFormatException e) {
            throw new IllegalArgumentException(ErrorCode.INPUT_DATE_NOT_VALID.getMessage());
        }
    }

    public static LocalDate parseToLocalDate(String input) {
        try {
            return LocalDate.parse(input);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(ErrorCode.INPUT_DATE_NOT_VALID.getMessage());
        }
    }

    public static LocalTime parseToLocalTime(String input) {
        try {
            return LocalTime.parse(input);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(ErrorCode.INPUT_TIME_NOT_VALID.getMessage());
        }
    }

    public static String parseNameFromDataLine(String line) {
        return line.split(",")[0];
    }

    public static LocalDate parseDateFromDataLine(String line) {
        return parseToLocalDate(line.split(",")[1].split(" ")[0]);
    }

    public static LocalTime parseTimeFromDataLIne(String line) {
        return parseToLocalTime(line.split(",")[1].split(" ")[1]);
    }
}
