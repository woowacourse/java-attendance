package attendance.exception;

public enum ErrorMessage {
    TIME_FORMAT_ERROR("시간 형식을 잘못 입력하셨습니다."),
    INVALID_DATE_RANGE("일 범위를 잘못 입력하셨습니다."),
    DATE_NUMBER_FORMAT("날짜는 숫자로 입력하셔야 합니다."),
    NOT_OPEN_CAMPUS("캠퍼스 운영 시간이 아닙니다"),
    NOT_ATTENDANCE_WEEKEND("주말에는 출석을 할 수 없습니다."),
    NOT_PRESENCE_COMMAND_OPTION("제공하지 않는 기능입니다."),
    ALREADY_PRESENCE_ATTENDANCE_RECORD("이미 출석 기록이 있습니다."),
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
