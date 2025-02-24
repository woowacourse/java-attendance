package exception;

public interface ExceptionMessage {

    String PREFIX = "[ERROR] ";

    default String getMessage(Object... args) {
        return PREFIX + String.format(getRawMessage(), args);
    }

    String getRawMessage();
}
