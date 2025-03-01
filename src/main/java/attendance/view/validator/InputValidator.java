package attendance.view.validator;

import attendance.exception.ExceptionMessage;
import attendance.exception.InputException;
import java.time.LocalDate;
import java.time.Month;

public class InputValidator {

    public static void validateBlank(String input) {
        if (input == null || input.isBlank()) {
            throw new InputException(ExceptionMessage.BLANK_INPUT.getMessage());
        }
    }

    public static void validateNonNumeric(String input) {
        try {
            int numeric = Integer.parseInt(input);
        } catch (NumberFormatException exception) {
            throw new InputException(ExceptionMessage.NOT_NUMERIC_INPUT.getMessage());
        }
    }

    public static void validateIsInMonth(int year, Month month, int day) {
        int lastDayInMonth = LocalDate.of(year, month, 1).lengthOfMonth();
        if (day < 1 || day > lastDayInMonth) {
            throw new InputException(ExceptionMessage.INVALID_DAY_INPUT.getMessage());
        }
    }
}
