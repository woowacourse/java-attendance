package util;

import static constant.ErrorMessage.INVALID_INPUT_NULL_OR_BLANK;
import static constant.ErrorMessage.INVALID_INTEGER_FORMAT;

public class InputValidator {

    private InputValidator() {
    }

    public static void validateNullOrBlank(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(INVALID_INPUT_NULL_OR_BLANK.getMessage());
        }
    }

    public static void validateInteger(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_INTEGER_FORMAT.getMessage());
        }
    }
}
