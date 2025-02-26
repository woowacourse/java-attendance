package controller;

import java.util.Arrays;

public enum MainMenuCommand {
    SAVE_ATTENDANCE_RECORD("1"),
    MODIFY_ATTENDANCE_RECORD("2"),
    PRINT_MONTH_ATTENDANCE_STATISTICS("3"),
    PRINT_RISK_CREWS("4"),
    QUIT("Q"),
    QUIT_SMALL_CASE("q"),
    NONE(""),
    ;

    private final String command;

    MainMenuCommand(String command) {
        this.command = command;
    }

    public static MainMenuCommand from(String option) {
        return Arrays.stream(values())
                .filter(mainMenuCommand -> option.equals(mainMenuCommand.command))
                .findAny()
                .orElse(NONE);
    }
}
