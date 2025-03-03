package attendance.model;

import java.util.Arrays;

public enum MenuOption {
    NONE(""),
    ATTEND_TODAY("1"),
    MODIFY_ATTENDANCE("2"),
    SHOW_STATISTIC("3"),
    CHECK_STATUS("4"),
    QUIT("Q");

    private final String option;

    MenuOption(final String option) {
        this.option = option;
    }

    public static MenuOption of(final String option) {
        return Arrays.stream(values())
                .filter(menuOption -> menuOption.getOption().equals(option))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 기능을 입력했습니다."));
    }

    public String getOption() {
        return option;
    }
}
