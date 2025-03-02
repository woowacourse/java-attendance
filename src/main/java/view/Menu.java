package view;

import java.util.Arrays;

public enum Menu {
    FIRST("1"),
    SECOND("2"),
    THIRD("3"),
    FOURTH("4"),
    QUIT("Q");

    private final String menuItem;

    Menu(String menuItem) {
        this.menuItem = menuItem;
    }

    public static Menu of(String menuItem) {
        return Arrays.stream(values())
                .filter(e -> e.menuItem.equals(menuItem))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 메뉴 선택입니다. 다시 입력해주세요."));
    }
}
