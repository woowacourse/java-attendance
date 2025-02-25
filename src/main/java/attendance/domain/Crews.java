package attendance.domain;

import attendance.exception.CustomException;
import attendance.exception.ErrorMessage;
import java.util.HashSet;
import java.util.Set;

public class Crews {

    private final Set<Crew> crews;

    private Crews(Set<Crew> crews) {
        this.crews = crews;
    }

    public static Crews initCrews() {
        Set<Crew> crews = new HashSet<>();
        return new Crews(crews);
    }

    public void addCrew(Crew crew) {
        crews.add(crew);
    }

    public boolean hasCrew(String crewName) {
        return crews.stream().anyMatch(crew -> crew.checkSameName(crewName));
    }

    public Crew findCrew(String crewName) {
        return crews.stream().filter(crew -> crew.checkSameName(crewName))
                .findFirst()
                .orElseThrow(() -> CustomException.from(ErrorMessage.NICKNAME_NOT_PRESENCE));
    }

    public Set<Crew> getCrews() {
        return new HashSet<>(crews);
    }

}


