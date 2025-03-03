package model.exception;

public class SystemException extends RuntimeException{
    public SystemException() {
        super("시스템 에러가 발생했습니다. 다시 이용해주시기 바랍니다.");
    }
}
