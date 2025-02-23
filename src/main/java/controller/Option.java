package controller;

import java.util.Arrays;

public enum Option {
    ATTEND("1"),
    EDIT_ATTENDANCE("2"),
    SHOW_ATTENDANCE_HISTORY("3"),
    SHOW_PENALTY_CREWS("4"),
    EXIT("Q");

    private final String value;

    Option(String value) {
        this.value = value;
    }

    public static Option validateValue(String value) {
        return Arrays.stream(values())
                .filter(option -> option.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 유효하지 않은 옵션 넘버입니다."));
    }
}
