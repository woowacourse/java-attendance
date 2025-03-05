package attendance.controller.constant;

import attendance.exception.CustomException;
import attendance.exception.ErrorMessage;
import java.util.Arrays;

public enum CommandOption {
    ATTENDANCE_CHECK("1"),
    ATTENDANCE_MODIFY("2"),
    ATTENDANCE_RECORD_CHECK("3"),
    PENALTY_CREWS_CHECK("4"),
    QUIT("Q");

    private final String name;

    CommandOption(final String name) {
        this.name = name;
    }

    public static CommandOption from(final String input) {
        return Arrays.stream(CommandOption.values())
                .filter(commandOption -> commandOption.name.equals(input))
                .findFirst()
                .orElseThrow(() -> CustomException.from(ErrorMessage.NOT_PRESENCE_COMMAND_OPTION));
    }

}
