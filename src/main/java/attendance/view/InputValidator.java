package attendance.view;

public final class InputValidator {
    private static final String WRONG_INPUT_NULL = "[ERROR] 입력값이 Null입니다.";
    private static final String WRONG_INPUT_EMPTY = "[ERROR] 입력값이 공백입니다.";
    private static final String WRONG_INPUT_TYPE_MUST_INT = "[ERROR] 입력값이 공백입니다.";

    private InputValidator() {
    }

    public static void validateIsEmpty(String input) {
        if (input == null) {
            throw new IllegalArgumentException(WRONG_INPUT_NULL);
        }
        if (input.isEmpty() || input.isBlank()) {
            throw new IllegalArgumentException(WRONG_INPUT_EMPTY);
        }
    }

    public static int validateInputTypeInteger(String input) {
        try {
            return Integer.parseInt(input);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(WRONG_INPUT_TYPE_MUST_INT, e);
        }
    }
}
