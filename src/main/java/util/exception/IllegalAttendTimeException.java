package util.exception;

public class IllegalAttendTimeException extends IllegalArgumentException {
    
    private static final String DEFAULT_MESSAGE = "출석 가능한 시간이 아닙니다. (출석 가능 시간 : %s ~ %s)";
    
    public IllegalAttendTimeException(String validTimeFrom, String validTimeTo) {
        super(DEFAULT_MESSAGE.formatted(validTimeFrom, validTimeTo));
    }
}
