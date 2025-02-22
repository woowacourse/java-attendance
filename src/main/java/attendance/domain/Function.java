package attendance.domain;

import java.util.Arrays;

public enum Function {
    ATTEND("1"),
    MODIFY_ATTENDANCE("2"),
    GET_ATTENDANCES("3"),
    GET_CREWS_AT_RISK_OF_EXPULSION("4"),
    QUIT("Q");

    private final String value;

    Function(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Function getFunction(String input) {

        return Arrays.stream(Function.values())
                .filter(function -> function.getValue().equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 올바른 기능을 입력해 주세요."));
    }
}
