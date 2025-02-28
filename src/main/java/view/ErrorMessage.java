package view;

public enum ErrorMessage {
    NOTICE_NOT_TRAINING_DAY("%02d월 %02d일 %s은 등교일이 아닙니다."),
    NOTICE_ATTENDANCE_ALREADY_EXISTED("이미 출석을 확인하였습니다. 필요한 경우 수정 기능을 이용해 주세요."),
    NOTICE_NICKNAME_IS_NOT_REGISTERED("등록되지 않은 닉네임입니다."),
    NOTICE_TIME_IS_NOT_A_CAMPUS_OPERATING_TIME("캠퍼스 운영 시간에만 출석이 가능합니다."),
    NOTICE_FUTURE_CAN_NOT_BE_MODIFIED("미래의 날짜는 수정할 수 없습니다."),
    NOTICE_FUNCTION_NUMBER_IS_NOT_EXISTED("선택창에 존재하는 기능만 선택 가능합니다.");

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