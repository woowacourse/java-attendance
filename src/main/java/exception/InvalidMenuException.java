package exception;

public class InvalidMenuException extends CustomException {
    public InvalidMenuException() {
        super("기능 번호가 올바르지 않습니다.");
    }
}
