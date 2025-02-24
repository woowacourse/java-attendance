package attendance.domain;

import static attendance.domain.CrewStatus.CLEAR;
import static attendance.domain.CrewStatus.calculateCrewStatus;
import static attendance.error.ErrorMessage.INVALID_CREW_NAME;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Crews {

    private final Set<Crew> crews;

    private Crews() {
        crews = new HashSet<>();
    }

    public static Crews create() {
        return new Crews();
    }

    public boolean addCrew(Crew crew) {
        return crews.add(crew);
    }

    public boolean contains(Crew crew) {
        return crews.contains(crew);
    }

    public Crew findByCrewName(String name) {
        return crews.stream()
            .filter(crew -> crew.isSameCrew(name))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException(INVALID_CREW_NAME.getMessage()));
    }

    public Set<Crew> getCrews() {
        return crews;
    }
}
