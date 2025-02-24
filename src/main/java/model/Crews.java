package model;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Crews {
    private final List<Crew> crews;

    public Crews(List<Crew> crews) {
        this.crews = crews;
    }

    public Optional<Crew> findCrewByName(String name) {
        return Optional.empty();
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
