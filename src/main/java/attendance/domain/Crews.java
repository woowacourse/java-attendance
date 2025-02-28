package attendance.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Crews {
    private final Set<Crew> crews;

    public Crews(Set<Crew> crews) {
        this.crews = new HashSet<>(crews);
    }

    public Crew findCrew(String inputCrewName) {
        return crews.stream()
                .filter(crew -> crew.getName().equals(inputCrewName))
                .findFirst()
                .orElseThrow();
    }

    public Set<Crew> getCrews() {
        return Collections.unmodifiableSet(crews);
    }

}
