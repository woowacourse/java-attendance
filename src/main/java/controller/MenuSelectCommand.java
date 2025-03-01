package controller;

public enum MenuSelectCommand {

    ATTEND("1"),
    UPDATE("2"),
    DISPLAY("3"),
    EXPULSION("4"),
    QUIT("Q");

    private final String key;

    MenuSelectCommand(String key) {
        this.key = key;
    }

    public static MenuSelectCommand from(String key) {
        for (MenuSelectCommand command : values()) {
            if (command.getKey().equalsIgnoreCase(key)) {
                return command;
            }
        }
        throw new IllegalArgumentException("올바른 기능을 선택해주세요.");
    }

    public String getKey() {
        return key;
    }
}
