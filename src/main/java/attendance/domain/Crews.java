package attendance.domain;

import attendance.exception.CustomException;
import attendance.exception.ErrorMessage;
import java.util.Collections;
import java.util.Set;

public class Crews {

    private final Set<Crew> crews;
    public Crews(final Set<Crew> crews) {
        this.crews = Collections.unmodifiableSet(crews);
    }

    public Crew findCrew(String crewName) {
        return crews.stream().filter(crew ->
                        crew.checkSameName(crewName))
                .findFirst()
                .orElseThrow(() -> CustomException.from(ErrorMessage.NICKNAME_NOT_PRESENCE));
    }

    public Set<Crew> getCrews() {
        return this.crews;
    }

}


