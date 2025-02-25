package domain;

import java.util.Arrays;

public enum Command {
    ATTEND("1"),
    UPDATE("2"),
    HISTORY("3"),
    PENALTY("4");

    private final String option;

    Command(String option) {
        this.option = option;
    }

    public static Command check(String inputOption) {
        return Arrays.stream(values())
                .filter(command -> command.option.equals(inputOption))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 유효하지 않은 옵션입니다."));
    }
}
