package attendance.model;

import java.util.HashSet;
import java.util.Set;

public class Crews {
    private final Set<Crew> crews;

    public Crews() {
        this.crews = new HashSet<>();
    }

    public void add(Crew crew) {
        crews.add(crew);
    }

    public Set<Crew> getCrews() {
        return crews;
    }
}
