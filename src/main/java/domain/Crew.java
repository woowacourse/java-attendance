package domain;

import java.util.Objects;

public class Crew {

    private final String name;

    private Crew(final String name) {

        if (name.isBlank()) {
            throw new IllegalArgumentException("닉네임은 필수 입니다.");
        }

        this.name = name;
    }

    public static Crew of(String name) {
        return new Crew(name);
    }

    public boolean isSame(final String name) {
        return this.name.equals(name);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Crew crew = (Crew) o;
        return Objects.equals(getName(), crew.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }
}

