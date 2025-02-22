package attendance.domain;

import java.util.Arrays;

public enum OperationCommand {

    ATTENDANCE_CONFIRMATION("1"),
    ATTENDANCE_MODIFICATION("2"),
    CREW_ATTENDANCES_CHECK("3"),
    EXPULSION_CHECK("4"),
    QUIT("Q");

    private final String commandText;


    OperationCommand(final String commandText) {
        this.commandText = commandText;
    }

    public static OperationCommand from(final String commandText) {
        return Arrays.stream(values())
                .filter(operationCommand -> operationCommand.commandText.equals(commandText.toUpperCase()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("제공되지 않는 기능입니다."));
    }
}
