package attendance.domain;

import java.util.Objects;

public class Crew {

    private String name;

    public Crew(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Crew crew)) {
            return false;
        }

        return Objects.equals(name, crew.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
