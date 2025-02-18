package domain;

public class Crew {
    private final String name;

    private Crew(String name) {
        validateEmptyName(name);
        String trimmed = name.trim();
        validateNameRange(trimmed);
        this.name = trimmed;
    }

    public static Crew of(String name) {
        return new Crew(name);
    }

    private void validateEmptyName(String name) {
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("[ERROR] 크루 이름은 NULL 또는 빈 값일 수 없습니다.");
        }
    }

    private void validateNameRange(String name) {
        if (name.length() < 2 || name.length() > 4) {
            throw new IllegalArgumentException("[ERROR] 크루 이름은 2글자 이상 4글자 이하여야 합니다.");
        }
    }
}
