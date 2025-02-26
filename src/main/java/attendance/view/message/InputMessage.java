package attendance.view.message;

public enum InputMessage {
    MENU("""
            오늘은 12월 14일 토요일입니다. 기능을 선택해 주세요.
            1. 출석 확인
            2. 출석 수정
            3. 크루별 출석 기록 확인
            4. 제적 위험자 확인
            Q. 종료""");

    private String content;

    InputMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
