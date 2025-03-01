package attendance.exception;

public class AttendanceArgumentException extends IllegalArgumentException {
    private static final String PREFIX = "[ERROR] ";

    public AttendanceArgumentException(String message) {
        super(PREFIX + message);
    }
}
