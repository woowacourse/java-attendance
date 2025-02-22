package exception;

import exception.parent.CustomException;

public class InvalidTimeException extends CustomException {
    public InvalidTimeException() {
        super("잘못된 형태의 시간 값입니다. HH:mm의 형태로 입력해주세요.");
    }
}
