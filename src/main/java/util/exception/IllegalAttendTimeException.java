package util.exception;

public class IllegalAttendTimeException extends IllegalArgumentException {
    
    private static final String DEFAULT_MESSAGE = "출석 가능한 시간이 아닙니다.";
    
    public IllegalAttendTimeException() {
        super(DEFAULT_MESSAGE);
    }
    
    public IllegalAttendTimeException(String message) {
        super(message);
    }
}
