package attendance.exception;

public class CustomException extends IllegalArgumentException {

    private CustomException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }

    public static CustomException from(ErrorMessage errorMessage) {
        return new CustomException(errorMessage);
    }

}