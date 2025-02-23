package exception.sub;

import exception.parent.CustomException;

public class AttendanceNotExistException extends CustomException {
    public AttendanceNotExistException() {
        super("출석하지 않은 날짜입니다.");
    }
}
