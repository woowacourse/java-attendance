package attendance.domain;

import java.util.Arrays;

public enum MenuCommand {
    ATTEND("1"),
    MODIFY("2"),
    LOOKUP("3"),
    EXPEL("4"),
    QUIT("Q");

    private static final String MENU_ERROR = "[ERROR] 지원하지 않는 기능입니다. 시스템을 종료합니다.";
    private final String command;

    MenuCommand(String command) {
        this.command = command;
    }

    public static MenuCommand toCommand(String input) {
        return Arrays.stream(values())
                .filter(command -> command.getCommand().equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(MENU_ERROR));
    }

    private String getCommand() {
        return command;
    }
}
