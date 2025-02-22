package exception;

import exception.parent.CustomException;

public class InvalidDateException extends CustomException {
    public InvalidDateException() {
        super("잘못된 일 값입니다. 월의 일에 속하는 정수로 입력해주세요.");
    }
}
