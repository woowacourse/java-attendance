package view;

import java.util.Arrays;

public enum Menu {
    CHECK_IN("1"),
    UPDATE_ATTENDANCE("2"),
    CHECK_ATTENDANCE_RECORDS("3"),
    CHECK_DISCIPLINED_CREWS("4"),
    QUIT("[Qq]");

    private final String menuRegex;

    Menu(String menuRegex) {
        this.menuRegex = menuRegex;
    }

    public static Menu from(String input) {
        return Arrays.stream(Menu.values())
                .filter(menu -> input.matches(menu.menuRegex))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 메뉴입니다."));
    }
}
