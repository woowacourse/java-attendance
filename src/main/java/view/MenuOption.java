package view;

import constant.ErrorMessage;

import java.util.Arrays;

public enum MenuOption {
    CHECK_ATTENDANCE("1"),
    MODIFY_ATTENDANCE("2"),
    CHECK_CREW_ATTENDANCE_HISTORY("3"),
    CHECK_PENALTY_RECEIVED_CREW("4"),
    QUIT("[qQ]");

    private final String option;

    MenuOption(String option) {
        this.option = option;
    }


    public static MenuOption of(String input) {
        return Arrays.stream(values()).filter(menu -> menu.option.equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_INPUT.getMessage()));
    }
}
