package domain;

import java.util.Arrays;

public enum AttendanceOperation {
    CHECK_IN("1"),
    MODIFY("2"),
    HISTORY("3"),
    RISK_CREW("4"),
    QUIT("Q");

    private final String code;

    AttendanceOperation(String code) {
        this.code = code;
    }

    public static AttendanceOperation from(String input) {
        return Arrays.stream(values())
                .filter(func -> func.code.equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 올바른 기능을 입력해 주세요."));
    }
}

