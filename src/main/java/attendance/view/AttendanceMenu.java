package attendance.view;

import java.util.Arrays;

public enum AttendanceMenu {
    CHECK("1"),
    UPDATE("2"),
    RECORD_SEARCH("3"),
    RISK_SEARCH("4"),
    QUIT("Q");

    private static final String INVALID_COMMAND = "[ERROR] 메뉴의 알맞은 커맨드를 입력해주세요.";

    private final String command;

    AttendanceMenu(final String command) {
        this.command = command;
    }

    public static AttendanceMenu find(final String input) {
        return Arrays.stream(values())
                .filter(menu -> menu.command.equals(input.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_COMMAND));
    }
}
