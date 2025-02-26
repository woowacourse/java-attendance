package view;

public enum ErrorMessage {
    NOTICE_NOT_TRAINING_DAY("%02d월 %02d일 %s은 등교일이 아닙니다."),
    NOTICE_ATTENDANCE_ALREADY_EXISTED("이미 출석을 확인하였습니다. 필요한 경우 수정 기능을 이용해 주세요.");


    private final static String ERROR_SIGN = "[ERROR] ";
    private final String format;
    private final static String INPUT_AGAIN_PROMPT = " 다시 입력해주세요.";

    ErrorMessage(String format) {
        this.format = format;
    }

    public String getFormat() {
        return ERROR_SIGN + format + INPUT_AGAIN_PROMPT;
    }

    public String format(Object... arg) {
        return String.format(ERROR_SIGN + format + INPUT_AGAIN_PROMPT, arg);
    }
}