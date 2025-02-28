package attendance.exception;

public class InputValidationException extends IllegalArgumentException {
    public InputValidationException(String message) {
        super("[ERROR] " + message);
    }

    public InputValidationException(String message, Throwable cause) {
        super("[ERROR] " + message, cause);
    }
}
