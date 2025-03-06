package exception;

public class AppException extends IllegalArgumentException {
    public static final String PREFIX = "[ERROR] ";

    public AppException(String message) {
        super(PREFIX + message);
    }
}
