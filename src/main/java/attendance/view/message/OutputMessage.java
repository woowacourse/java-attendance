package attendance.view.message;

public enum OutputMessage {
    MENU("""
            오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요.
            1. 출석 확인
            2. 출석 수정
            3. 크루별 출석 기록 확인
            4. 제적 위험자 확인
            Q. 종료"""),
    RECORD("%d월 %d일 %s %s (%s)"),
    UPDATED(" -> %s (%s) 수정 완료"),
    MONTHLY_RECORD_HEADER("이번 달 %s의 출석 기록입니다."),
    ATTENDANCE_STATE("""
            출석: %d회
            지각: %d회
            결석: %d회"""),
    ATTENDANCE_STATE_RESULT("%s 대상자입니다."),
    RISK_HEADER("제적 위험자 조회 결과"),
    RISK_INFO("- %s: 결석 %d회, 지각 %d회 (%s)"),
    BLANK_TIME("--:--");

    private final String content;

    OutputMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
