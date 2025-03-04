package view.input;

public enum InputPrompt {
    GUIDE_FUNCTION_INPUT("기능을 선택해 주세요."),

    GUIDE_INPUT_NAME_TO_CHECK_ATTENDANCE("출석 확인을 위한 닉네임을 입력해 주세요."),
    GUIDE_INPUT_TIME_TO_CHECK_ATTENDANCE("출석 확인을 위한 등교 시간을 입력해 주세요."),

    GUIDE_INPUT_NAME_TO_MODIFY_ATTENDANCE("출석을 수정하려는 크루의 닉네임을 입력해 주세요."),
    GUIDE_INPUT_DATE_TO_MODIFY_ATTENDANCE("수정하려는 날짜(일)를 입력해 주세요."),
    GUIDE_INPUT_TIME_TO_MODIFY_ATTENDANCE("언제로 변경하겠습니까?"),

    GUIDE_INPUT_NAME_TO_CHECK_ATTENDANCE_RECORD("출석 기록 확인을 위한 닉네임을 입력해 주세요.");

    private final String format;

    InputPrompt(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

    public String format(Object... arg) {
        return String.format(format, arg);
    }
}