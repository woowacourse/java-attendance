package attendance.view;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public final class InputValidator {
    private static final String WRONG_INPUT_NULL = "입력값이 Null입니다.";
    private static final String WRONG_INPUT_EMPTY = "입력값이 공백입니다.";
    private static final String WRONG_INPUT_TYPE_INT = "입력값은 1~31 이내의 숫자여야합니다.";
    private static final String WRONG_INPUT_TIME_FORMAT = "시간 입력은 --:--와 같은 형태야합니다: ";

    private InputValidator() {
    }

    public static void validateIsEmpty(String input) {
        if (input == null) {
            throw new InputValidationException(WRONG_INPUT_NULL);
        }
        if (input.isEmpty() || input.isBlank()) {
            throw new InputValidationException(WRONG_INPUT_EMPTY);
        }
    }

    public static int validateInputDate(String input) {
        validateIsEmpty(input);
        try {
            int parseInt = Integer.parseInt(input);
            validateDate(parseInt);
            return parseInt;
        } catch (IllegalArgumentException e) {
            throw new InputValidationException(WRONG_INPUT_TYPE_INT, e);
        }
    }

    private static void validateDate(int parseInt) {
        if (parseInt < 1 || parseInt > 31) {
            throw new InputValidationException(WRONG_INPUT_TYPE_INT);
        }
    }

    public static LocalTime validateInputTimeFormat(String input) {
        validateIsEmpty(input);
        try {
            return LocalTime.parse(input);
        } catch (DateTimeParseException e) {
            throw new InputValidationException(WRONG_INPUT_TIME_FORMAT + input);
        }
    }

    private static class InputValidationException extends IllegalArgumentException {
        public InputValidationException(String message) {
            super("[ERROR] " + message);
        }

        public InputValidationException(String message, Throwable cause) {
            super("[ERROR] " + message, cause);
        }
    }
}
