package attendance.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Crews {
    private final List<Crew> crews;

    public Crews() {
        this.crews = new ArrayList<>();
    }

    public void addCrew(String crewName) {
        this.crews.add(new Crew(crewName));
    }

    public List<Crew> getCrews() {
        return Collections.unmodifiableList(crews);
    }

}
