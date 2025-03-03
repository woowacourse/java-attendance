package util.exception;

public class IllegalAttendDateException extends IllegalArgumentException {
    
    private static final String DEFAULT_MESSAGE = "출석 가능한 날짜가 아닙니다.";
    
    public IllegalAttendDateException() {
        super(DEFAULT_MESSAGE);
    }
    
    public IllegalAttendDateException(String message) {
        super(message);
    }
}
