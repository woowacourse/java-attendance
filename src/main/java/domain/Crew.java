package domain;

import java.util.Objects;

public class Crew {

    private final String name;

    public Crew(final String name) {
        this.name = name;
    }

    public static Crew from(final String name) {
        return new Crew(name);
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
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
