package controller.exception;

public class ProgramQuitException extends RuntimeException {
    public static final String MESSAGE = "프로그램을 종료합니다.";

    public ProgramQuitException(String message) {
        super(message);
    }
}
