package util.validator;

import static util.constant.ErrorMessage.FUNCTION_NUMBER_ERROR_MESSAGE;

import java.util.Set;

public class InputValidator {

    private InputValidator() {
    }

    public static void checkFunctions(String functionNumber, Set<String> functions) {
        if (!functions.contains(functionNumber) && !functionNumber.equals("Q")) {
            throw new IllegalArgumentException(FUNCTION_NUMBER_ERROR_MESSAGE);
        }
    }
}
