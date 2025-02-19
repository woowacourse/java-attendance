package attendance.exception;

public class AttendanceStateException extends IllegalStateException {

    public AttendanceStateException(String message) {
        super("[ERROR] " + message);
    }
}
