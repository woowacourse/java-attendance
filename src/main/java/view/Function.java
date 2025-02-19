package view;

import java.util.Arrays;
import java.util.Objects;

public enum Function {
    CHECK_ATTENDANCE("1", "출석 확인"),
    EDIT_ATTENDANCE("2", "출석 수정"),
    CHECK_ATTENDANCE_OF_CREW("3", "크루별 출석 기록 확인"),
    CHECK_WARNING_CREW("4", "제적 위험자 확인"),
    QUIT("Q", "종료"),
    ;

    private final String option;
    private final String name;

    Function(String option, String name) {
        this.option = option;
        this.name = name;
    }

    @Override
    public String toString() {
        return option + ". " + name;
    }

    public static Function getFunction(String input) {
        return Arrays.stream(Function.values())
                .filter(function -> Objects.equals(function.option, input))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 올바르지 않은 기능에 대한 입력입니다."));
    }
}
