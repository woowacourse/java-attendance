package domain;

import java.util.Arrays;

public enum Function {
    ATTEND("1"),
    EDIT("2"),
    CHECK("3"),
    CHECK_EXPELLED_WARNING("4"),
    QUIT("Q");

    private final String functionNumber;

    Function(String functionNumber) {
        this.functionNumber = functionNumber;
    }

    public static Function from(String functionNumber) {
        return Arrays.stream(values())
                .filter(f->f.functionNumber.equals(functionNumber))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 번호입니다."));
    }
}
