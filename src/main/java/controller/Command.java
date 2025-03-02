package controller;

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
        throw new IllegalArgumentException("옵션이 존재하지 않습니다.");
    }

    public boolean isOne() {
        return this == ONE;
    }

    public boolean isTwo() {
        return this == TWO;
    }
}
