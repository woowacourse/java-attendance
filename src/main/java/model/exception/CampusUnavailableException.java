package model.exception;

public class CampusUnavailableException extends IllegalArgumentException{
    public CampusUnavailableException() {
        super("캠퍼스 운영 시간이 아닙니다.");
    }
}
