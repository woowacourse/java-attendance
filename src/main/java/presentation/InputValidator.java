package presentation;

import static presentation.ControllerCommand.*;

public class InputValidator {
    public static final String INVALID_DATETIME_ARGUMENT_EXCEPTION = "잘못된 형식을 입력하였습니다.";

    public static void commandValidate(String command) {
        if (!command.equals(ATTEND_COMMAND.getCommand())
                && !command.equals(EDIT_COMMAND.getCommand())
                && !command.equals(CREW_QUERY_COMMAND.getCommand())
                && !command.equals(CREWS_WARNING_COMMAND.getCommand())
                && !command.equalsIgnoreCase(EXIT_COMMAND.getCommand())) {
            throw new IllegalArgumentException(INVALID_DATETIME_ARGUMENT_EXCEPTION);
        }
    }
}
