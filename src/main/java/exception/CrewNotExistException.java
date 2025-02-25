package exception;

public class CrewNotExistException extends IllegalArgumentException{
    public CrewNotExistException() {
        super("닉네임에 맞는 크루를 찾을 수 없습니다.");
    }
}
