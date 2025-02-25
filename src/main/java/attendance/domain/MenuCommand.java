package attendance.domain;

import attendance.controller.OptionCheckAttendance;
import attendance.controller.OptionLookupAttendance;
import attendance.controller.OptionLookupExpulsion;
import attendance.controller.OptionModifyAttendance;
import java.util.Arrays;

public enum MenuCommand {
    ATTEND("1", OptionCheckAttendance.class),
    MODIFY("2", OptionModifyAttendance.class),
    LOOKUP("3", OptionLookupAttendance.class),
    EXPEL("4", OptionLookupExpulsion.class),
    QUIT("Q", null);

    private static final String MENU_COMMAND_ERROR_MESSAGE = "[ERROR] 지원하지 않는 기능 값입니다.";
    private static final String COMMAND_PATTERN = "^[1-4|Q]$";

    private final String command;
    private final Class<?> option;

    MenuCommand(String command, Class<?> option) {
        this.command = command;
        this.option = option;
    }

    public static MenuCommand toCommand(final String input) {
        if (input == null || !input.matches(COMMAND_PATTERN)) {
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

    public Class<?> getOption() {
        return option;
    }
}
