package exception;

public class InvalidMonthValueException extends CustomException {
    public InvalidMonthValueException() {
        super("유효하지 않은 달(month) 입니다.");
    }
}
