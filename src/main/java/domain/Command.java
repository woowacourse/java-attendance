package domain;

public enum Command {

    TODAY_ATTEND("1"),
    CHANGE_CREW_ATTENDANCE("2"),
    SHOW_CREW_ATTENDANCE("3"),
    SHOW_DANGEROUS_CREW("4"),
    EXIT("Q");

    private final String command;

    Command(String command) {
        this.command = command;
    }

    public static Command getCommand(String inputCommand) {
        for(Command command1 : Command.values())
            if(inputCommand.equals(command1.command))
                return command1;
        throw new IllegalArgumentException("잘못 된 입력입니다.");
    }
}
