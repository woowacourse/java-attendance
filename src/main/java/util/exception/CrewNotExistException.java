package util.exception;

public class CrewNotExistException extends IllegalArgumentException {
    
    private static final String DEFAULT_MESSAGE = "%s는 존재하는 크루가 아닙니다.";
    
    public CrewNotExistException(String name) {
        super(DEFAULT_MESSAGE.formatted(name));
    }
}
