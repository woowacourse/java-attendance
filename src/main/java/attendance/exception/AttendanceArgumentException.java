package attendance.exception;

public class AttendanceArgumentException extends IllegalArgumentException {

    public AttendanceArgumentException(String message) {
        super("[ERROR] " + message);
    }
}
