package attendance.domain;

import java.util.Arrays;

public enum Function {

    ADD_ATTENDANCE("1"),
    MODIFY_ATTENDANCE("2"),
    GET_CREW_ATTENDANCES("3"),
    GET_EXPULSION_CANDIDATES("4"),
    QUIT("Q");

    private final String value;

    Function(final String value) {

        this.value = value;
    }

    public static Function getFunction(final String input) {

        return Arrays.stream(Function.values())
                .filter(function -> function.value.equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 올바른 기능을 입력해 주세요."));
    }
}
