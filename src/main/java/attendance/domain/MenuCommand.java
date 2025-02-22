package attendance.domain;

import java.util.Arrays;

public enum MenuCommand {
    ATTEND("1"),
    MODIFY("2"),
    LOOKUP("3"),
    EXPEL("4"),
    QUIT("Q");

    private static final String MENU_COMMAND_ERROR_MESSAGE = "[ERROR] 지원하지 않는 기능 값입니다.";

    private final String command;

    MenuCommand(String command) {
        this.command = command;
    }

    public static MenuCommand toCommand(final String input) {
        if (input == null) {
            throw new IllegalArgumentException(MENU_COMMAND_ERROR_MESSAGE);
        }
        return Arrays.stream(values())
                .filter(command -> command.getCommand().equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(MENU_COMMAND_ERROR_MESSAGE));
    }

    private String getCommand() {
        return command;
    }
}
