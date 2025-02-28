package domain;

public enum Feature {
    ATTENDANCE_CHECK("1", "출석 확인"),
    ATTENDANCE_EDIT("2", "출석 수정"),
    CREW_RECORDS_CHECK("3", "크루별 출석 기록 확인"),
    EXPELLED_WARNING_CHECK("4", "제적 위험자 확인"),
    EXIT("Q", "종료");

    private final String functionNumber;
    private final String functionName;

    Feature(String functionNumber, String functionName) {
        this.functionNumber = functionNumber;
        this.functionName = functionName;
    }

    public static boolean isProvided(String input) {
        return false;
    }
}
