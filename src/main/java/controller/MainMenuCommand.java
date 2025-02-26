package controller;

import java.util.Arrays;

public enum MainMenuCommand {
    SAVE_ATTENDANCE_RECORD("1"),
    MODIFY_ATTENDANCE_RECORD("2"),
    PRINT_MONTH_ATTENDANCE_STATISTICS("3"),
    PRINT_RISK_CREWS("4"),
    QUIT("Q"),
    ;

    private final String command;

    MainMenuCommand(String command) {
        this.command = command;
    }

    public static MainMenuCommand from(String option) {
        return Arrays.stream(values())
                .filter(mainMenuCommand -> option.equals(mainMenuCommand.command))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(option + ": 존재하지 않는 커맨드입니다."));
    }
}
