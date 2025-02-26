package exception;

public class CrewNotExistException extends CustomException {
    public CrewNotExistException() {
        super("존재하지 않는 크루입니다.");
    }
}
