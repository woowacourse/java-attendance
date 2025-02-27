package controller.exception;

public class ServiceNotExistException extends IllegalArgumentException{
    public ServiceNotExistException() {
        super("해당하는 번호의 기능을 찾을 수 없습니다.");
    }
}
