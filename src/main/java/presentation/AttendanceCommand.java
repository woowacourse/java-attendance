package presentation;

public enum AttendanceCommand {
    ATTENDANCE_COMMAND("1"),
    EDIT_COMMAND("2"),
    GET_CREW_INFO_COMMAND("3"),
    CREWS_WARNING_COMMAND("4"),
    EXIT_COMMAND("Q"),
    NONE_COMMAND(""),
    ;

    private final String command;

    AttendanceCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
