package view.output;

public enum OutputPrompt {
    DISPLAY_FUNCTION_SELECTION_PROMPT("""
            오늘은 %02d월 %02d일 %s입니다.
            1. 출석 확인
            2. 출석 수정
            3. 크루별 출석 기록 확인
            4. 제적 위험자 확인
            Q. 종료"""),

    DISPLAY_CHECK_ATTENDANCE_RESULT("%02d월 %02d일 %s %s (%s)"),

    DISPLAY_MODIFY_ATTENDANCE_RESULT("%02d월 %02d일 %s %s (%s) -> %s (%s) 수정 완료!"),

    DISPLAY_ATTENDANCE_RECORD_PROMPT("이번 달 %s의 출석 기록입니다."),
    DISPLAY_ATTENDANCE_RECORD_BY_CREW("%02d월 %02d일 %s %s (%s)"),
    DISPLAY_ATTENDANCE_RECORD_COUNT_BY_CREW("""
            출삭: %d회
            지각: %d회
            결석: %d회
            """),
    DISPLAY_PENALTY_BY_CREW("%s 대상자입니다."),

    DISPLAY_PENALTY_CREWS_PROMPT("제적 위험자 조회 결과"),
    DISPLAY_PENALTY_CREWS_RESULT("- %s: 결석 %d회, 지각 %d회 (%s)");

    private final String format;

    OutputPrompt(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

    public String format(Object... args) {
        return String.format(format, args);
    }
}