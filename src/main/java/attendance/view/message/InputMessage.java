package attendance.view.message;

public enum InputMessage {
    MENU("""
            오늘은 %s입니다. 기능을 선택해 주세요.
            1. 출석 확인
            2. 출석 수정
            3. 크루별 출석 기록 확인
            4. 제적 위험자 확인
            Q. 종료"""),
    NICKNAME("닉네임을 입력해 주세요."),
    ARRIVAL_TIME("등교 시간을 입력해 주세요."),
    NICKNAME_FOR_UPDATE("출석을 수정하려는 크루의 닉네임을 입력해 주세요."),
    DAY_FOR_UPDATE("수정하려는 날짜(일)를 입력해 주세요."),
    TIME_FOR_UPDATE("언제로 변경하겠습니까?");

    private String content;

    InputMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
