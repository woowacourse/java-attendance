package domain;

import java.util.Arrays;

public enum Feature {
    ATTENDANCE_CHECK("1", "출석 확인"),
    ATTENDANCE_EDIT("2", "출석 수정"),
    CREW_RECORDS_CHECK("3", "크루별 출석 기록 확인"),
    EXPELLED_WARNING_CHECK("4", "제적 위험자 확인"),
    EXIT("Q", "종료");

    private static final String NOT_PROVIDED_ERROR_MESSAGE = "제공하지 않는 기능입니다.";

    private final String functionNumber;
    private final String functionName;

    Feature(String functionNumber, String functionName) {
        this.functionNumber = functionNumber;
        this.functionName = functionName;
    }

    public static Feature of(String input) {

    }

    public static void validateProvided(String input) {
        Arrays.stream(values())
            .filter(feature -> feature.functionNumber.equals(input))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(NOT_PROVIDED_ERROR_MESSAGE));
    }
}