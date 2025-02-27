package attendance.util;

public enum ErrorMessage {

    NICKNAME_MISSING_ERROR("크루의 닉네임은 반드시 존재해야 합니다.")
    ;

    private static final String PREFIX = "[ERROR] ";

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
