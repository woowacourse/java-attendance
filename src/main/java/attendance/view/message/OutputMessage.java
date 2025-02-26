package attendance.view.message;

public enum OutputMessage {
    RECORD("%s %s (%s)"),
    UPDATE_RESULT(" -> %s (%s) 수정 완료!"),
    SEARCH_RESULT_HEADER("이번 달 %s의 출석 기록입니다."),
    ATTENDANCE_STATE(
            """
                     출석: %d회
                     지각: %d회
                     결석: %d회
                    """),
    RISK_TYPE("%s 대상자입니다."),
    RISK_CREW_HEADER("제적 위험자 조회 결과"),
    RISK_CREW_CONTENT("- %s: 결석 %d회, 지각 %d회 (%s)"),
    EMPTY_TIME("--:--");

    private final String content;

    OutputMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
