package attendance.view.validator;

import attendance.exception.ExceptionMessage;

public class InputValidator {

    public static void validateIsNumeric(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            String message = ExceptionMessage.NOT_NUMERIC_INPUT.getContent();
            throw new IllegalArgumentException(message);
        }
    }
}
