package domain;

import java.util.Arrays;

public enum Command {
    ATTENDANCE_REGISTER("1"),
    ATTENDANCE_UPDATE("2"),
    ATTENDANCE_CHECK("3"),
    PENALTY_CHECK("4");

    private final String commandOption;

    Command(String commandOption) {
        this.commandOption = commandOption;
    }

    public static Command identify(String inputOption) {
        return Arrays.stream(values())
                .filter(command -> command.commandOption.equals(inputOption))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 커맨드는 존재하지 않습니다."));
    }
}
