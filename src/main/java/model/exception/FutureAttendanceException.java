package model.exception;

public class FutureAttendanceException extends IllegalArgumentException{

    public FutureAttendanceException() {
        super("오늘 날짜 이전의 출석만 등록 또는 수정해주세요.");
    }
}
