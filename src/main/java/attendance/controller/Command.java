package attendance.controller;

import java.util.Arrays;

public enum Command {

    ATTENDANCE("1", "출석 확인"),
    UPDATE_ATTENDANCE("2", "출석 수정"),
    GET_ATTENDANCE_LOG("3", "크루별 출석 기록 확인"),
    GET_REQUIRES_MANAGEMENT_CREWS("4", "제적 위험자 확인"),
    QUIT("Q", "종료");

    private final String option;
    private final String description;

    Command(String option, String description) {
        this.option = option;
        this.description = description;
    }

    public static Command from(String option) {
        return Arrays.stream(values())
                .filter(command -> command.option.equals(option))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 기능은 존재하지 않습니다."));
    }

    public String getOption() {
        return option;
    }

    public String getDescription() {
        return description;
    }
}
