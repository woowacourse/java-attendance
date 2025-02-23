package attendance.controller.validator.exception.message;

public class ExceptionMessage {
    private static final String PREFIX = "[ERROR] ";

    public static final String NOT_OPEN_DATE = PREFIX + "%d월 %d일 %s은 등교일이 아닙니다.";
    public static final String NOT_OPEN_TIME = PREFIX + "캠퍼스 운영 시간이 아닙니다.";
}
