package attendance.domain;

import static attendance.domain.exception.MenuExceptionMessage.NOT_IN_MENU;

import java.util.Arrays;

public enum Menu {
    CHECK_ATTEND("1"),
    UPDATE_ATTEND("2"),
    PRINT_ATTEND_BY_CREW("3"),
    PRINT_WARNING("4"),
    QUIT("Q");

    private final String select;

    Menu(String select) {
        this.select = select;
    }

    public static Menu of(String input) {
        return Arrays.stream(Menu.values())
                .filter(menu -> menu.select.equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(NOT_IN_MENU));
    }
}
