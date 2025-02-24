package model;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Crews {
    private final List<Crew> crews;

    public static Crews of(List<String> crewNames) {
        List<Crew> crews = crewNames.stream()
                .map(Crew::new)
                .toList();
        return new Crews(crews);
    }

    public Crews(List<Crew> crews) {
        this.crews = crews;
    }

    public Optional<Crew> findCrewByName(String name) {
        return crews.stream()
                .filter(crew -> crew.equals(new Crew(name)))
                .findAny();
        //TODO : getName대신 이거 써도 되나..? 메모리에반데
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
