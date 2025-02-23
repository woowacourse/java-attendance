package attendance.common.exception;

public class AttendanceArgumentException extends IllegalArgumentException {

    public AttendanceArgumentException(String message) {
        super("[ERROR] " + message);
    }
}
