package attendance.domain;

import java.util.Arrays;

public enum Menu {
    ATTEND("1"),
    UPDATE_ATTENDANCE("2"),
    PRINT_ATTENDANCES_BY_CREW("3"),
    PRINT_WARNING("4"),
    QUIT("Q");

    private final String select;

    Menu(String select) {
        this.select = select;
    }

    public static Menu from(String input) {
        return Arrays.stream(Menu.values())
                .filter(menu -> menu.select.equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 메뉴를 잘못 선택하셨습니다."));
    }
}