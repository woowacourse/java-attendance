package domain;

import java.util.List;

public class Crews {
    private final List<Crew> crews;

    public Crews(final List<Crew> crews) {
        this.crews = crews;
    }

    public Crew findByName(final String crewName) {
        return crews.stream()
                .filter(crew -> crew.matchName(crewName))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
