package attendance.exception;

public enum ErrorMessage {

    ALREDAY_PRESENCE_ATTENDANCE_RECORD("이미 출석 기록이 있습니다."),
    NOT_ATTENDANCE_RECORD("출석 기록이 없습니다."),
    NOT_FIND_CREW("해당 크루를 찾을 수 없습니다."),
    FILE_NOT_PRESENCE("파일이 존재하지 않습니다.");


    private final String PREFIX = "[ERROR] ";
    private final String message;

    ErrorMessage(String message) {
        this.message = PREFIX + message;
    }

    public String getMessage() {
        return this.message;
    }
}
