package util.exception;

public class WeekendAttendException extends IllegalArgumentException {
    
    private static final String DEFAULT_MESSAGE = "주말에는 출석할 수 없습니다.";
    
    public WeekendAttendException() {
        super(DEFAULT_MESSAGE);
    }
}
