package attendance.exception;

public class InputValidationException extends IllegalArgumentException {
    public InputValidationException(String message) {
        super("[ERROR] " + message);
    }
}
