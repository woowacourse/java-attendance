package util;

import static constant.ErrorMessage.INVALID_INPUT_NULL_OR_BLANK;

public class InputValidator {

    private InputValidator() {
    }

    public static void validateNullOrBlank(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(INVALID_INPUT_NULL_OR_BLANK.getMessage());
        }
    }
}
