package attendance.model;

import static attendance.error.ErrorMessage.ERROR_CREW_NOT_FOUND;

import java.util.ArrayList;
import java.util.List;

public class Crews {
    private final List<Crew> crews;

    public Crews() {
        this.crews = new ArrayList<>();
    }

    public void add(Crew crew) {
        crews.add(crew);
    }

    public List<Crew> getCrews() {
        return crews;
    }

    public Crew findCrew(String name) {
        return crews.stream()
                .filter(crew -> crew.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ERROR_CREW_NOT_FOUND));
    }

    public boolean containsCrew(String name) {
        return crews.stream()
                .anyMatch(crew -> crew.getName().equals(name));
    }

}
