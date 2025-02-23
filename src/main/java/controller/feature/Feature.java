package controller.feature;

import java.util.Arrays;

public enum Feature {
    CHECK_IN("1"),
    MODIFY_CHECK_IN("2"),
    READ_CHECK_IN("3"),
    READ_DANGER_CREWS("4"),
    QUIT("Q"),
    ;
    private final String code;

    Feature(String code) {
        this.code = code;
    }

    public static Feature from(String code) {
        return Arrays.stream(values())
                .filter(feature -> feature.code.equals(code))
                .findFirst()
                .orElseThrow(
                        () -> new IllegalArgumentException("[ERROR] 1, 2, 3, 4, Q 만 입력해주세요.")
                );
    }
}
