package domain;

import java.util.Arrays;

public enum MenuOption {
    CHECK_ATTENDANCE("1"),
    CHANGE_ATTENDANCE("2"),
    SHOW_CREW_ATTENDANCES("3"),
    SHOW_ALERT_CREW("4"),
    EXIT("Q");

    final String menuOption;

    MenuOption(String menuOption) {
        this.menuOption = menuOption;
    }

    public static MenuOption getMenuOption(String menuOption) {
        return Arrays.stream(MenuOption.values())
                .filter(option -> option.menuOption.equals(menuOption))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바른 기능 입력이 아닙니다."));
    }


    public static boolean isExit(String menuOption) {
        if (menuOption.equals(EXIT.menuOption)) {
            return true;
        }
        return false;
    }
}
