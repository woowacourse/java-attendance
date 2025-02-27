package attendance.view;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import attendance.exception.InputValidationException;

public class InputView {
    private static final String WRONG_INPUT_NULL = "입력값이 비어있습니다.";
    private static final String WRONG_INPUT_EMPTY = "입력값이 공백입니다.";
    private static final String WRONG_INPUT_INT_IN_MONTHLY = "입력값은 1~31 이내의 숫자여야합니다.";
    private static final String WRONG_INPUT_TIME_FORMAT = "시간 입력은 --:--와 같은 형태야합니다: ";

    private final Scanner scanner = new Scanner(System.in);

    public String input() {
        var input = scanner.nextLine();
        validateIsEmpty(input);
        return input;
    }

    public int inputDayInMonthly() {
        var input = scanner.nextLine();
        return validateInputDate(input);
    }

    public LocalTime inputTime() {
        var input = scanner.nextLine();
        return validateInputTimeFormat(input);
    }

    private void validateIsEmpty(String input) {
        if (input == null) {
            throw new InputValidationException(WRONG_INPUT_NULL);
        }
        if (input.isEmpty() || input.isBlank()) {
            throw new InputValidationException(WRONG_INPUT_EMPTY);
        }
    }

    private int validateInputDate(String input) {
        validateIsEmpty(input);
        try {
            int parseInt = Integer.parseInt(input);
            validateDateInMonthly(parseInt);
            return parseInt;
        } catch (IllegalArgumentException e) {
            throw new InputValidationException(WRONG_INPUT_INT_IN_MONTHLY, e);
        }
    }

    private void validateDateInMonthly(int parseInt) {
        if (parseInt < 1 || parseInt > 31) {
            throw new InputValidationException(WRONG_INPUT_INT_IN_MONTHLY);
        }
    }

    private LocalTime validateInputTimeFormat(String input) {
        validateIsEmpty(input);
        try {
            return LocalTime.parse(input);
        } catch (DateTimeParseException e) {
            throw new InputValidationException(WRONG_INPUT_TIME_FORMAT + input);
        }
    }
}
