package exception;

public class DuplicateAttendanceException extends CustomException {
    public DuplicateAttendanceException() {
        super("이미 출석한 날짜입니다.");
    }
}
