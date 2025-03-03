package domain;

import java.io.Serializable;

public enum ErrorCode {
    ATTENDANCE_DATE_DUPLICATED("이미 출석한 날짜입니다. 수정 기능을 이용해주세요."),
    ATTENDANCE_DATE_NOT_FOUND("해당 날짜의 출석 기록이 존재하지 않습니다."),
    CREW_NAME_NOT_FOUND("등록되지 않은 닉네임입니다."),
    DATE_NOT_ATTENDING_DATE("%s은 등교일이 아닙니다."),
    TIME_NOT_OPERATION_HOUR("%s는 캠퍼스 운영 시간이 아닙니다."),
    INPUT_DATE_NOT_VALID("잘못된 날짜 입력입니다."),
    INPUT_TIME_NOT_VALID("잘못된 시간 입력입니다."),
    INPUT_SELECTION_NOT_FOUND("입력에 해당하는 선택지가 존재하지 않습니다."),
    INPUT_CONSOLE_READER_FAILED("입력 값을 읽는 도중 오류가 발생했습니다."),
    INPUT_ATTEMPT_LIMIT_EXCEEDED("입력 횟수를 초과하였습니다. 다시 입력해주세요.");

    private static final String ERROR_PREFIX = "[ERROR] ";
    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return ERROR_PREFIX + message;
    }

    public String getFormattedMessage(Serializable... args) {
        return String.format(message, args);
    }
}
