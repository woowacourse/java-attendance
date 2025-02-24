package model;

import java.util.List;
import java.util.Objects;

public class Crews {
    private final List<Crew> crews;

    public Crews(List<Crew> crews) {
        this.crews = crews;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Crews targetCrews)) {
            return false;
        }
        return crews.containsAll(targetCrews.crews)
                && targetCrews.crews.containsAll(crews);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(crews);
    }
}
