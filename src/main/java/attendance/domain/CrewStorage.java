package attendance.domain;

import java.util.HashSet;
import java.util.Set;

public class CrewStorage {

    private final Set<Crew> crews = new HashSet<>();

    public void add(Crew crew) {
        crews.add(crew);
    }

    public boolean isContained(String crewName) {
        return crews.stream().anyMatch(crew -> crew.isSameName(crewName));
    }
}
