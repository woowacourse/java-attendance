package attendance.domain;

import static attendance.domain.exception.CommandExceptionMessage.NOT_EXIST_COMMAND;

public enum Command {
    CONFIRM_ATTENDANCE("1"),
    UPDATE_ATTENDANCE("2"),
    PRINT_CREW_ATTENDANCES("3"),
    PRINT_WARNING_EXPULSION_CREWS("4");

    private final String value;

    Command(final String value) {
        this.value = value;
    }

    public static Command of(String commandInput) {
        for (Command command : values()) {
            if (command.value.equals(commandInput)) {
                return command;
            }
        }
        throw new IllegalArgumentException(NOT_EXIST_COMMAND);
    }
}
