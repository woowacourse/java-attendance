package domain;

import java.util.Arrays;

public enum FeatureType {

    ATTENDANCE_CHECK("1"),
    ATTENDANCE_UPDATE("2"),
    ATTENDANCE_RECORD("3"),
    READ_ABSENCE("4"),
    EXIT("Q");

    private final String command;

    FeatureType(final String command) {
        this.command = command;
    }

    public static FeatureType findBy(final String feature) {
        return Arrays.stream(values())
                .filter(value -> value.command.equals(feature))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기능입니다."));
    }

    public static boolean isExitType(final String feature) {
        return EXIT.command.equals(feature);
    }
}
