package attendance.view;

import java.util.Arrays;

public enum OperationCommand {

    ATTENDANCE_CONFIRMATION("1"),
    ATTENDANCE_MODIFICATION("2"),
    CREW_ATTENDANCES_INQUIRY("3"),
    PENALTY_CREWS_INQUIRY("4"),
    QUIT("Q");

    private final String commandText;

    OperationCommand(final String commandText) {
        this.commandText = commandText;
    }

    public static OperationCommand from(final String commandInput) {
        return Arrays.stream(values())
                .filter(command -> command.commandText.equals(commandInput))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기능 입니다."));
    }

}
