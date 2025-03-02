package attendance.view;

import java.io.Closeable;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import attendance.exception.InputValidationException;

public class InputView implements Closeable {
    private static final String WRONG_INPUT = "입력값이 잘못되었습니다.";
    private static final String WRONG_INPUT_FORMAT_TIME = "시간 입력 형태는 --:--의 형태여야 합니다:";

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void close() {
        scanner.close();
    }

    public String inputString() {
        String input = scanner.nextLine();
        if (input.isEmpty() || input.isBlank()) {
            throw new InputValidationException(WRONG_INPUT);
        }
        return input;
    }

    public LocalTime requestTime() {
        var input = scanner.nextLine();
        try {
            return LocalTime.parse(input);
        } catch (DateTimeParseException e) {
            throw new InputValidationException(WRONG_INPUT_FORMAT_TIME + input);
        }
    }
}
