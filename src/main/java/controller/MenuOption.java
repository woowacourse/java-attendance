package controller;

import static util.Constants.ERROR_HEADER;

import java.util.Arrays;
import java.util.regex.Pattern;

public enum MenuOption {
    ADDING_ATTENDANCE("[1]"),
    UPDATING_ATTENDANCE("[2]"),
    SHOWING_CREW_ATTENDANCE_HISTORY("[3]"),
    SHOWING_PENALTY_CREWS("[4]"),
    QUIT("[Qq]");

    private static final String MENU_NOT_EXISTED_ERROR = "해당 메뉴가 존재하지 않습니다.";

    private final String pattern;

    MenuOption(String pattern) {
        this.pattern = pattern;
    }

    public static MenuOption from(String input) {
        return Arrays.stream(values())
                .filter(menuOption ->
                        Pattern.matches(menuOption.pattern, input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ERROR_HEADER + MENU_NOT_EXISTED_ERROR));
    }

    public static boolean isRunningOption(MenuOption menuOption) {
        return menuOption != MenuOption.QUIT;
    }
}
