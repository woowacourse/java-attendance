package domain;

public class Crew {
    private final String name;

    public Crew(String name) {
        validateName(name);
        this.name = name;
    }

    public boolean isNameMatch(String name) {
        return this.name.equals(name);
    }

    public String getName() {
        return name;
    }

    private void validateName(String name) {
        if (name.length() > 4 || name.length() < 2) {
            throw new IllegalArgumentException("닉네임은 2글자 이상, 4글자 이하여야 합니다.");
        }
    }
}
