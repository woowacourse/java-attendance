package constant;

public enum InputViewMessage {

    ATTENDANCE_OPTION_PROMPT("""
            오늘은 %s월 %s일 %s입니다. 기능을 선택해 주세요.
            1. 출석 확인
            2. 출석 수정
            3. 크루별 출석 기록 확인
            4. 제적 위험자 확인
            Q. 종료"""),

    ATTENDANCE_CHECK_IN_NICKNAME_PROMPT("닉네임을 입력해 주세요."),
    ATTENDANCE_CHECK_IN_TIME_PROMPT("등교 시간을 입력해 주세요."),

    ATTENDANCE_UPDATE_NICKNAME_PROMPT("출석을 수정하려는 크루의 닉네임을 입력해 주세요."),
    ATTENDANCE_UPDATE_DAY_PROMPT("수정하려는 날짜(일)를 입력해 주세요."),
    ATTENDANCE_UPDATE_TIME_PROMPT("언제로 변경하겠습니까?"),

    ATTENDANCE_HISTORY_NICKNAME_PROMPT("닉네임을 입력해 주세요."),
    ;

    private final String message;

    InputViewMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
