package util.validator;

import java.util.Set;

public class InputValidator {

    private static final String FUNCTION_NUMBER_ERROR_MESSAGE = "유효하지 않은 기능입니다.";
    private static final String NULL_INPUT_ERROR_MESSAGE = "입력값이 없습니다.";
    private static final String NOT_INTEGER_ERROR_MESSAGE = "숫자가 아닙니다.";

    private InputValidator() {
    }

    public static void checkFunctions(String functionNumber, Set<String> functions) {
        if (!functions.contains(functionNumber) && !functionNumber.equals("Q")) {
            throw new IllegalArgumentException(FUNCTION_NUMBER_ERROR_MESSAGE);
        }
    }

    public static void checkNull(String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException(NULL_INPUT_ERROR_MESSAGE);
        }
    }

    public static void checkInteger(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(NOT_INTEGER_ERROR_MESSAGE);
        }
    }
}
