package attendance;

import java.util.Arrays;

public enum AttendanceMenu {
    CHECK("1"),
    UPDATE("2"),
    SEARCH("3"),
    WARNED_CREW("4"),
    QUIT("Q");

    private final String command;

    AttendanceMenu(String command) {
        this.command = command;
    }

    public static AttendanceMenu find(String input) {
        return Arrays.stream(values())
                .filter(menu -> menu.command.equals(input.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 메뉴의 알맞은 커맨드를 입력해주세요."));
    }
}
