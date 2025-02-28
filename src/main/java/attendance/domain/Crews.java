package attendance.domain;

import static attendance.error.ErrorMessage.ERROR_CREW_NOT_EXIST;

import java.util.Collections;
import java.util.Map;

public class Crews {
    private final Map<String, Crew> crewRegistry;

    public Crews(Map<String, Crew> crewRegistry) {
        this.crewRegistry = crewRegistry;
    }

    public Crew findByName(String name) {
        Crew crew = crewRegistry.get(name);
        if (crew == null) {
            throw new IllegalArgumentException(ERROR_CREW_NOT_EXIST);
        }
        return crew;
    }

    public Map<String, Crew> getAllCrews() {
        return Collections.unmodifiableMap(crewRegistry);
    }
}
