package domain;

import java.util.Arrays;
import java.util.Objects;

public enum UserSelection {
    CHECK_ATTENDANCE("1", "출석 확인"),
    MODIFY_ATTENDANCE("2", "출석 수정"),
    GET_ATTENDANCE_RECORDS("3", "크루별 출석 기록 확인"),
    GET_CREWS_WITH_PENALTY("4", "제적 위험자 확인"),
    QUIT("Q", "종료"),
    ;

    private final String input;
    private final String message;

    UserSelection(String input, String message) {
        this.input = input;
        this.message = message;
    }

    public static UserSelection findByInput(String value) {
        return Arrays.stream(UserSelection.values())
                .filter(userSelection -> Objects.equals(userSelection.input, value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.INPUT_SELECTION_NOT_FOUND.getMessage()));
    }

    public String getInput() {
        return input;
    }

    public String getMessage() {
        return message;
    }
}
