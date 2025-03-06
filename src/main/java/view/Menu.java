package view;

import java.util.Arrays;
import java.util.Objects;

public enum Menu {

    CHECK_ATTENDANCE("1"),
    UPDATE_ATTENDANCE("2"),
    CREW_ATTENDANCE_RECORD("3"),
    PENALTY_RECORD("4"),
    QUIT("Q");

    private final String menuSelector;

    Menu(String menuSelector) {
        this.menuSelector = menuSelector;
    }

    public static Menu findBySelector(String inputMenuSelector) {
        return Arrays.stream(Menu.values())
                .filter(menu -> Objects.equals(menu.menuSelector, inputMenuSelector))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 메뉴를 찾을 수 없습니다"));
    }

    public boolean isQuit() {
        return this == QUIT;
    }
}
