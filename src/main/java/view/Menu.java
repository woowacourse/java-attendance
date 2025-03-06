package view;

import java.util.Arrays;

public enum Menu {
    CHECK_ATTENDANCE("1"),
    EDIT_ATTENDANCE("2"),
    GET_ALL_ATTENDANCE("3"),
    GET_DANGEROUS_CREW("4"),
    QUIT("Q");

    private final String command;

    Menu(String command) {
        this.command = command;
    }

    public static Menu from(String input) {
        return Arrays.stream(Menu.values())
                .filter(menu -> input.matches(menu.command))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 메뉴입니다."));
    }
}
