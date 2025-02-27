package attendance.view.validator;

import attendance.exception.ExceptionMessage;

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
}
