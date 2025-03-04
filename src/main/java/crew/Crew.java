package crew;

import java.util.Objects;

public class Crew {
    private final String name;

    public Crew(String name) {
        this.name = name;
    }

    public boolean hasSameName(String name) {
        return this.name.equals(name);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Crew crew)) {
            return false;
        }
        return Objects.equals(name, crew.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
