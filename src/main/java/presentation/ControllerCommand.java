package presentation;

public enum ControllerCommand {
    ATTEND_COMMAND("1"),
    EDIT_COMMAND("2"),
    CREW_QUERY_COMMAND("3"),
    CREWS_WARNING_COMMAND ("4"),
    EXIT_COMMAND("Q"),
    ERROR_COMMAND("");

    private final String command;

    ControllerCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public static ControllerCommand convertCommand(String command){
        if(command.equals(ATTEND_COMMAND.getCommand())) return ATTEND_COMMAND;
        if(command.equals(EDIT_COMMAND.getCommand())) return EDIT_COMMAND;
        if(command.equals(CREW_QUERY_COMMAND.getCommand())) return CREW_QUERY_COMMAND;
        if(command.equals(CREWS_WARNING_COMMAND.getCommand())) return CREWS_WARNING_COMMAND;
        if(command.equalsIgnoreCase(EXIT_COMMAND.getCommand())) return EXIT_COMMAND;
        return ERROR_COMMAND;
    }
}
