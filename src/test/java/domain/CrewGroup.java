package domain;

import java.util.Map;

public class CrewGroup {

    private final Map<String, Crew> crews;

    public CrewGroup(final Map<String, Crew> crews) {
        this.crews = crews;
    }

    public Crew findByName(final String name) {
        return crews.get(name);
    }
}
