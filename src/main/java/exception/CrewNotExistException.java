package exception;

import exception.parent.CustomException;

public class CrewNotExistException extends CustomException {
    public CrewNotExistException() {
        super("등록되지 않은 닉네임입니다.");
    }
}
