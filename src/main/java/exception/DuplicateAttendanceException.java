package exception;

import exception.parent.CustomException;

public class DuplicateAttendanceException extends CustomException {
    public DuplicateAttendanceException() {
        super("이미 출석한 날짜입니다.");
    }
}
