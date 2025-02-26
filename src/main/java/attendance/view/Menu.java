package attendance.view;

import java.util.Arrays;

public enum Menu {
    CHECK("1"),
    UPDATE("2"),
    RECORD_SEARCH("3"),
    RISK_CREW_SEARCH("4"),
    QUIT("Q");

    private final String command;

    Menu(final String command) {
        this.command = command;
    }

    public static Menu find(final String input) {
        return Arrays.stream(values())
                .filter(menu -> menu.command.equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 잘못된 메뉴 선택의 입력입니다."));
    }
}
