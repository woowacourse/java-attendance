package view.output;

public enum OutputPrompt {
    DISPLAY_FUNCTION_SELECTION_PROMPT("""
            오늘은 %02d월 %02d일 %s입니다.
            1. 출석 확인
            2. 출석 수정
            3. 크루별 출석 기록 확인
            4. 제적 위험자 확인
            Q. 종료""");


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