package exception;

public class InvalidDateException extends CustomException {
    public InvalidDateException() {
        super("공휴일에는 출석을 기록할 수 없습니다.");
    }
}
