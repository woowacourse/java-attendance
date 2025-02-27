package attendance.view.validator;

import attendance.exception.ExceptionMessage;
import java.time.LocalDate;
import java.time.Month;

public class InputValidator {

    public static void validateBlank(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(ExceptionMessage.BLANK_INPUT.getMessage());
        }
    }

    public static void validateNonNumeric(String input) {
        try {
            int numeric = Integer.parseInt(input);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(ExceptionMessage.NOT_NUMERIC_INPUT.getMessage());
        }
    }

    public static void validateIsInMonth(int year, Month month, int day) {
        int lastDayInMonth = LocalDate.of(year, month, 1).lengthOfMonth();
        if (day < 1 || day > lastDayInMonth) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_DAY_INPUT.getMessage());
        }
    }
}
