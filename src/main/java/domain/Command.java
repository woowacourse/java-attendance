package domain;

import java.util.Arrays;

public enum Command {
    CHECK_ATTENDEES("출석 확인", "1"),
    EDIT_ATTENDANCE("출석 수정", "2"),
    CHECK_THE_ATTENDANCE_RECORD_BY_CREW("크루별 출석 기록 확인", "3"),
    CONFIRMATION_OF_THOSE_AT_RISK_OF_EXPULSION("제적 위험자 확인", "4"),
    QUIT("종료", "Q");

    private String commandName;
    private String commandNumber;

    Command(final String commandName, final String commandNumber) {
        this.commandName = commandName;
        this.commandNumber = commandNumber;
    }

    public static Command findByCommandNumber(final String commandNumber) {
        return Arrays.stream(Command.values())
                .filter(c -> c.commandNumber.equals(commandNumber))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("알맞은 명령어를 입력하세요."));
    }

    public String getCommandName() {
        return commandName;
    }

    public String getCommandNumber() {
        return commandNumber;
    }
}
