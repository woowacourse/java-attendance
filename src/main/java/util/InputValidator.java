package util;

import static constant.ErrorMessage.INVALID_INPUT_NULL_OR_BLANK;
import static constant.ErrorMessage.INVALID_INTEGER_FORMAT;
import static constant.ErrorMessage.INVALID_TIME_FORMAT;

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

    public static void validateTime(String input) {
        String timeRegex = "^([0-1]\\d|2[0-3]):[0-5]\\d$";

        if (!input.matches(timeRegex)) {
            throw new IllegalArgumentException(INVALID_TIME_FORMAT.getMessage());
        }
    }
}
