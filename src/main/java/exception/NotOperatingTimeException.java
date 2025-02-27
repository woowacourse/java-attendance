package exception;

public class NotOperatingTimeException extends CustomException {
    public NotOperatingTimeException() {
        super("캠퍼스 운영 시간이 아닙니다.");
    }
}
