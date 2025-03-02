package controller;

import java.util.Arrays;

public enum Command {

    ATTEND("1"),
    MODIFY_ATTENDANCE("2"),
    READ_ATTENDANCE_LOG("3"),
    READ_DISCIPLINARY_CREWS("4"),
    QUIT("Q"),
    NONE("");

    private final String code;

    Command(String code) {
        this.code = code;
    }

    public static Command from(String code) {
        return Arrays.stream(Command.values())
                .filter(command -> command.code.equals(code))
                .findAny()
                .filter(command -> command != NONE)
                .orElseThrow(() -> new IllegalArgumentException("1, 2, 3, 4, Q 중 하나를 입력해주세요."));
    }
}
