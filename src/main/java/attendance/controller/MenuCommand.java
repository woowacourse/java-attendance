package attendance.controller;

import attendance.exception.ExceptionMessage;
import attendance.exception.MenuException;
import java.util.List;

public enum MenuCommand {
    FIRST("1"),
    SECOND("2"),
    THIRD("3"),
    FOURTH("4"),
    QUIT("Q");

    private final String content;

    MenuCommand(String content) {
        this.content = content;
    }

    public static MenuCommand parse(String input) {
        String upperInput = input.toUpperCase();
        List<MenuCommand> commands = List.of(MenuCommand.values());
        return commands.stream()
                .filter(command -> command.getContent().equals(upperInput))
                .findAny()
                .orElseThrow(() -> new MenuException(ExceptionMessage.INCORRECT_MENU.getMessage()));
    }

    public String getContent() {
        return content;
    }
}
