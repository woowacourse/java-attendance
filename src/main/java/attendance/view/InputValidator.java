package attendance.view;

public final class InputValidator {

    private static final String NUMERIC_INPUT_MESSAGE = "[ERROR] 숫자만 입력 가능합니다.";

    private InputValidator() {
    }

    public static void validateIsNumeric(final String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(NUMERIC_INPUT_MESSAGE);
        }
    }
}
