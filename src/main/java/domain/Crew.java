package domain;

import exception.AppException;

public class Crew {
    private final String name;

    private Crew(String name) {
        validateNameLength(name);
        validateNameLanguage(name);
        this.name = name;
    }

    public static Crew of(String name) {
        return new Crew(name);
    }

    private void validateNameLength(String name) {
        if (name.length() < 2 || name.length() > 4) {
            throw new AppException("이름은 2글자에서 4글자 사이여야 합니다.");
        }
    }

    private void validateNameLanguage(String name) {
        if (!name.matches("^[가-힣]+$")) {
            throw new AppException("이름은 한글이어야 합니다.");
        }
    }
}
