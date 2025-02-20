package view;

public enum OutputMessages {
    DATE_PROMPT("오늘은 %02d월 %02d일 %s입니다. 기능을 선택해 주세요."),
    FIRST_FUNCTION_PROMPT("1. 출석 확인"),
    SECOND_FUNCTION_PROMPT("2. 출석 수정"),
    THIRD_FUNCTION_PROMPT("3. 크루별 출석 기록 확인"),
    FOURTH_FUNCTION_PROMPT("4. 제적 위험자 확인"),
    QUIT_FUNCTION_PROMPT("Q. 종료"),
    GUIDE_PROMPT("(원하는 기능의 번호 입력)");

    private final String format;

    OutputMessages(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

    public String format(Object... args) {
        return String.format(format, args);
    }
}