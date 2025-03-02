package attendance.controller;

import java.util.Arrays;

public enum CommandStatus {

    ATTEND("1"),
    MODIFY("2"),
    INQUIRY_CREW("3"),
    FIND_DISMISSAL("4"),
    QUIT("Q");

    private final String status;

    CommandStatus(final String status) {
        this.status = status;
    }


    public static CommandStatus from(final String statusInput) {
        return Arrays.stream(CommandStatus.values())
                .filter(commandStatus -> commandStatus.status.equals(statusInput))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하는 기능이 아닙니다."));
    }
}
