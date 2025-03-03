package model.exception;

public class DuplicatedAttendanceRegistrationException extends IllegalArgumentException{
    public DuplicatedAttendanceRegistrationException() {
        super("이미 출석한 날짜엔 출석 확인 기능을 사용할 수 없습니다. 출석 수정 기능을 이용해주세요.");
    }
}
