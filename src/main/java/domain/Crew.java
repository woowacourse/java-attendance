package domain;

import java.util.Objects;

public class Crew {
    private final String name;

    public Crew(final String name) {
        this.name = name;
    }

    public boolean matchName(final String crewName) {
        return this.name.equals(crewName);
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

        return Objects.equals(name, crew.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    public String getName() {
        return this.name;
    }
}
