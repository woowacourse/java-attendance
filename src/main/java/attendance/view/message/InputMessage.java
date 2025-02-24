package attendance.view.message;

public enum InputMessage {
    NICK_NAME("닉네임을 입력해 주세요."),
    ARRIVAL_TIME("등교 시간을 입력해 주세요"),
    NICK_NAME_FOR_UPDATE("출석을 수정하려는 크루의 닉네임을 입력해 주세요."),
    ARRIVAL_TIME_FOR_UPDATE("언제로 변경하겠습니까?"),
    DATE_FOR_UPDATE("수정하려는 날짜(일)를 입력해 주세요.");

    private final String content;

    InputMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
