package attendance.domain;

import java.util.Arrays;

public enum MenuCommand {
    CHECK("1"),
    MODIFY("2"),
    LOOKUP("3"),
    EXPEL("4"),
    QUIT("Q");

    private static final String INVALID_COMMAND_TYPE = "[ERROR] 올바르지 않은 명령어입니다. 지정된 명령어 중 하나를 입력해주세요.\n";
    private static final String COMMAND_TYPES = "^[1-4|Q]$";

    private final String command;

    MenuCommand(String commandInput) {
        this.command = commandInput;
    }

    public static MenuCommand of(final String commandInput) {
        if (!commandInput.matches(COMMAND_TYPES)) {
            throw new IllegalArgumentException(INVALID_COMMAND_TYPE);
        }
        return Arrays.stream(MenuCommand.values())
                .filter(menuCommand -> menuCommand.getCommand().equals(commandInput))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_COMMAND_TYPE));
    }

    public String getCommand() {
        return command;
    }
}
