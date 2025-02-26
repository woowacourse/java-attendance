package attendance.view;

import java.util.Arrays;

public enum MenuOption {

    CHECK_ATTENDANCE("1"),
    MODIFY_ATTENDANCE("2"),
    CHECK_ATTENDANCE_BY_CREW("3"),
    CHECK_DISMISSAL_CREW("4"),
    QUIT("Q");

    private final String command;

    MenuOption(String command) {
        this.command = command;
    }

    public static MenuOption from(final String input) {
        return Arrays.stream(MenuOption.values())
                .filter(menuOption -> menuOption.command.equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 입력입니다."));
    }
}
