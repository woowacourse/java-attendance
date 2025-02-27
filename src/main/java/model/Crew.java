package model;

import java.util.Objects;

public class Crew {
    private final String name;

    public Crew(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Crew targetCrew)) {
            return false;
        }
        return name.equals(targetCrew.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
