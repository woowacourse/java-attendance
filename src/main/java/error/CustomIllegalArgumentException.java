package error;

public class CustomIllegalArgumentException extends IllegalArgumentException {
    public CustomIllegalArgumentException(final String message) {
        super("[ERROR] " + message);
    }
}
