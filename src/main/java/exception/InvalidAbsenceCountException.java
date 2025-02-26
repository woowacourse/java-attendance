package exception;

public class InvalidAbsenceCountException extends CustomException {
    public InvalidAbsenceCountException() {
        super("결석 횟수가 올바르지 않습니다.");
    }
}
