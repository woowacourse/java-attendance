package controller;

import java.util.Arrays;

public enum MenuOption {

    REGISTER_ATTENDANCE("1"),
    EDIT_ATTENDANCE("2"),
    SHOW_CREW_ATTENDANCE("3"),
    SHOW_EXPELLED_CREWS("4"),
    QUIT("Q")
    ;

    private final String command;

    MenuOption(String command) {
        this.command = command;
    }

    public static MenuOption findOptionByCommand(String input) {
        return Arrays.stream(MenuOption.values())
                .filter(option -> option.command.equals(input))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 유효하지 않은 옵션입니다."));
    }
}
