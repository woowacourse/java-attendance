package controller.command;

public enum Command {
    ATTEND_COMMAND("1"),
    EDIT_COMMAND("2"),
    CREW_INFO_COMMAND("3"),
    WARNING_CREW_COMMAND("4"),
    EXIT_COMMAND("Q"),
    ;
    private final String command;

    Command(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
