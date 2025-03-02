package domain;

import java.util.Map;
import java.util.Objects;

public class CrewGroup {

    private final Map<String, Crew> crews;

    public CrewGroup(final Map<String, Crew> crews) {
        this.crews = crews;
    }

    public boolean containsCrew(final String crewName) {
        return crews.containsKey(crewName);
    }

    public Crew findByName(final String name) {
        return crews.entrySet().stream()
                .filter(entry -> Objects.equals(entry.getKey(), name))
                .map(Map.Entry::getValue)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다."));
    }
}
