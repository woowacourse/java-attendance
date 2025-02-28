package attendance.exception;

public enum ErrorMessage {
    NOT_OPERATION_DATE("[ERROR] %02d월 %02d일 %s은 등교일이 아닙니다."),
    NO_ATTENDANCE_TO_MODIFY("[ERROR] 수정할 출석 기록이 없습니다."),
    NOT_EXISTS_OPTION("[ERROR] 존재하지 않는 옵션입니다."),
    DUPLICATED_ATTENDANCE("[ERROR] 이미 출석하셨습니다."),
    NOT_EXISTS_CREW_NICKNAME("[ERROR] 등록되지 않은 닉네임입니다."),
    NOT_OPERATION_TIME("[ERROR] 캠퍼스 운영 시간은 매일 08:00 ~ 23:00 입니다."),
    INVALID_TIME("유효한 시간을 입력해주세요."),
    INVALID_DATE("유효한 날짜를 입력해주세요.");

    private final String message;

    ErrorMessage(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
