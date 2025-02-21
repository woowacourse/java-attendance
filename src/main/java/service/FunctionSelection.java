package service;

import java.util.Arrays;

public enum FunctionSelection {
    CHECK_ATTENDANCE("1"),
    MODIFY_ATTENDANCE("2"),
    CHECK_ATTENDANCE_RECORD("3"),
    CHECK_PENALTY_CREWS("4"),
    QUIT("Q");

    private final String userInput;

    FunctionSelection(String userInput) {
        this.userInput = userInput;
    }

    public static FunctionSelection getFunctionByInput(String input) {
        return Arrays.stream(FunctionSelection.values())
                .filter(functionSelection -> functionSelection.getUserInput().equals(input))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 입력이 올바르지 않습니다."));
    }

    public String getUserInput() {
        return userInput;
    }
}