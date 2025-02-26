package attendance.domain;

import static attendance.error.ErrorMessage.ERROR_CREW_ALREADY_EXIST;
import static attendance.error.ErrorMessage.ERROR_CREW_NOT_EXIST;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Crews {
    private final Map<String, Crew> crewMap = new HashMap<>();

    public void addCrew(Crew crew) {
        if (crewMap.containsKey(crew.getName())) {
            throw new IllegalArgumentException(ERROR_CREW_ALREADY_EXIST);
        }
        crewMap.put(crew.getName(), crew);
    }

    public Crew findByName(String name) {
        Crew crew = crewMap.get(name);
        if (crew == null) {
            throw new IllegalArgumentException(ERROR_CREW_NOT_EXIST);
        }
        return crew;
    }

    public boolean contains(String name) {
        return crewMap.containsKey(name);
    }

    public Map<String, Crew> getAllCrews() {
        return Collections.unmodifiableMap(crewMap);
    }
}
