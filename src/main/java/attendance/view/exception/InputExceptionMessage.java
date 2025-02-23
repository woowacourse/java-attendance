package attendance.view.exception;

public class InputExceptionMessage {
    private static final String PREFIX = "[ERROR] ";

    public static final String DATE_NOT_INTEGER = PREFIX + "날짜(일)는 숫자로 입력해주세요.";
    public static final String INVALID_DATE_RANGE = PREFIX + "날짜는 %d일부터 %d일 사이를 입력해주세요.";
}
