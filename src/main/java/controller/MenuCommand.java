package controller;

import java.util.Arrays;

public enum MenuCommand {
    SAVE_ATTENDANCE_RECORD("1"),
    MODIFY_ATTENDANCE_RECORD("2"),
    PRINT_MONTH_ATTENDANCE_STATISTICS("3"),
    PRINT_RISK_CREWS("4"),
    QUIT("Q"),
    QUIT_SMALL_CASE("q"),
    NONE(""),
    ;

    private final String command;

    MenuCommand(String command) {
        this.command = command;
    }

    public static MenuCommand from(String option) {
        return Arrays.stream(values())
                .filter(menuCommand -> option.equals(menuCommand.command))
                .findAny()
                .orElse(NONE);
    }
}
