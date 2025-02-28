package view;

import java.util.Arrays;

public enum Function {
    CHECK_ATTENDANCE("1"),
    MODIFY_ATTENDANCE("2"),
    CHECK_ATTENDANCE_RECORD_BY_CREW_NAME("3"),
    CHECK_PENALTY_CREWS("4"),
    QUIT("Q");

    private final String selection;

    Function(String selection) {
        this.selection = selection;
    }

    public static Function checkFunctionNumber(String functionSelection) {
        return Arrays.stream(Function.values())
                .filter(selection -> selection.selection.equals(functionSelection))
                .findAny()
                .orElseThrow();
    }
}