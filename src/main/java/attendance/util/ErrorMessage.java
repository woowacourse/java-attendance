package attendance.util;

public enum ErrorMessage {

    NICKNAME_MISSING_ERROR("크루의 닉네임은 반드시 존재해야 합니다."),
    CREW_DUPLICATE_ERROR("같은 크루를 중복하여 추가할 수 없습니다."),
    FILE_READ_ERROR("파일 읽기 오류가 발생했습니다."),
    ATTENDANCE_ALREADY_EXIST_ERROR("이미 출석을 완료했습니다. 수정 기능을 이용해주세요."),
    ATTENDANCE_NOT_EXIST_ERROR("수정하려는 날짜에 출석 기록이 없습니다."),
    CREW_NICKNAME_NOT_EXIST_ERROR("등록되지 않은 닉네임입니다."),
    INPUT_NULL_OR_BLANK_ERROR("값을 입력해주세요."),
    INVALID_INPUT_OPTION_ERROR("올바른 옵션을 선택해주세요.")
    ;

    private static final String PREFIX = "[ERROR] ";

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return PREFIX + message;
    }
}
