package attendance.view;

import java.util.Arrays;

public enum OperationCommand {

    ATTENDANCE_CONFIRMATION("1"),
    ATTENDANCE_MODIFICATION("2"),
    CREW_ATTENDANCES_CHECK("3"),
    EXPULSION_CHECK("4"),
    QUIT("Q");

    private final String commandText;


    OperationCommand(String commandText) {
        this.commandText = commandText;
    }

    public static OperationCommand from(String commandText) {
        return Arrays.stream(values())
                .filter(operationCommand -> operationCommand.commandText.equals(commandText))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("제공되지 않는 기능입니다."));
    }

    public boolean isAttendanceConfirmation() {
        return this.equals(ATTENDANCE_CONFIRMATION);
    }

    public boolean isAttendanceModification() {
        return this.equals(ATTENDANCE_MODIFICATION);
    }

    public boolean isCrewAttendancesCheck() {
        return this.equals(CREW_ATTENDANCES_CHECK);
    }
}
