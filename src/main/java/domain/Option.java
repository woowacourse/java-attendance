package domain;

import java.util.Arrays;

public enum Option {

    ATTEND("1"),
    EDIT("2"),
    DISPLAY_ATTENDANCE_SUMMARY("3"),
    DISPLAY_EXPULSION_RISK_CREW("4"),
    QUIT("Q");

    private final String inputNumber;

    Option(String inputNumber) {
        this.inputNumber = inputNumber;
    }

    public static Option from(String inputNumber) {
        return Arrays.stream(values())
                .filter(f -> f.inputNumber.equalsIgnoreCase(inputNumber))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 유효하지 않은 입력입니다."));
    }
}
