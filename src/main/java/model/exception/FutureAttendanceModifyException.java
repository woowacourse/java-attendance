package model.exception;

public class FutureAttendanceModifyException extends IllegalArgumentException{

    public FutureAttendanceModifyException() {
        super("오늘 날짜 이전의 출석만 수정 가능합니다.");
    }
}
