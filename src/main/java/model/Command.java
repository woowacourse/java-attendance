package model;

import java.util.Arrays;

public enum Command {

    ATTENDANCE_CHECK("1", "출석 확인"),
    ATTENDANCE_CORRECTION("2", "출석 수정"),
    CHECK_ATTENDANCE_RECORDS_BY_CREW("3", "크루별 출석 기록 확인"),
    IDENTIFICATION_OF_THE_RISK_OF_EXPULSION("4", "제적 위험자 확인"),
    QUIT("Q", "종료");

    private final String symbol;
    private final String displayName;

    Command(final String symbol, final String displayName) {
        this.symbol = symbol;
        this.displayName = displayName;
    }

    public static Command findBySymbol(final String symbolInput) {
        return Arrays.stream(Command.values())
                .filter(o -> o.symbol.equals(symbolInput))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 명령어 입니다."));
    }

    public String getSymbol() {
        return symbol;
    }

    public String getDisplayName() {
        return displayName;
    }
}
