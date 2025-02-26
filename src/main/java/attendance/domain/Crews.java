package attendance.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Crews {
    private final Map<String, Crew> crewMap = new HashMap<>();

    public void addCrew(Crew crew) {
        if (crewMap.containsKey(crew.getName())) {
            throw new IllegalArgumentException("이미 존재하는 크루입니다.");
        }
        crewMap.put(crew.getName(), crew);
    }

    public Crew findByName(String name) {
        Crew crew = crewMap.get(name);
        if (crew == null) {
            throw new IllegalArgumentException("해당 크루가 존재하지 않습니다.");
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
