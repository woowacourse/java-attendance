package constant;

public enum Command {

    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    QUIT("Q"),
    ;

    private final String value;

    Command(String value) {
        this.value = value;
    }

    public static Command find(String rawCommand) {
        for (Command command : Command.values()) {
            if (rawCommand.equals(command.value)) {
                return command;
            }
        }
        throw new IllegalArgumentException("존재하지 않는 옵션입니다.");
    }
}
