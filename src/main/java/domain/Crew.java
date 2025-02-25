package domain;

import java.util.Objects;

public class Crew {
    private final String name;

    public Crew(String name) {
        validateSize(name);
        this.name = name;
    }

    private void validateSize(String name) {
        if (name.length() < 2 || name.length() > 4) {
            throw new IllegalArgumentException("크루 이름은 2글자 이상 4글자 이하여야 합니다.");
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Crew crew = (Crew) o;
        return Objects.equals(name, crew.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
