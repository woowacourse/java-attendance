package attendance.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Crews {
    private final List<Crew> crews;

    public Crews(List<Crew> crews) {
        this.crews = new ArrayList<>(crews);
    }

    public List<Crew> getCrews() {
        return Collections.unmodifiableList(crews);
    }
}
