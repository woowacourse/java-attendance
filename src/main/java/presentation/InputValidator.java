package presentation;

public class InputValidator {
    public static final String INVALID_DATETIME_ARGUMENT_EXCEPTION = "잘못된 형식을 입력하였습니다.";

    public static void commandValidate(String command) {
        if (!command.equals("1") && !command.equals("2") && !command.equals("3") && !command.equals("4")
                && !command.equals("Q") && !command.equals("q")) {
            throw new IllegalArgumentException(INVALID_DATETIME_ARGUMENT_EXCEPTION);
        }
    }
}
