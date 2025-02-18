package attendance.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Optional<Crew> findCrew(Crew crew) {
        return crews.stream()
                .filter(c -> c.getName().equals(crew.getName()))
                .findFirst();
    }

}
