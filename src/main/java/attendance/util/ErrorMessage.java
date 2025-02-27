package attendance.util;

public enum ErrorMessage {

    NICKNAME_MISSING_ERROR("크루의 닉네임은 반드시 존재해야 합니다."),
    CREW_DUPLICATE_ERROR("같은 크루를 중복하여 추가할 수 없습니다."),
    FILE_READ_ERROR("파일 읽기 오류가 발생했습니다.")
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
