package domain;

import java.util.HashSet;
import java.util.Set;

public class Crews {
    private final Set<Crew> crews;

    public Crews() {
        crews = new HashSet<>();
    }

    public Crew initCrew(String name) {
        if (!hasCrew(name)) {
            Crew crew = new Crew(name);
            crews.add(crew);
            return crew;
        }
        return getCrew(name);
    }

    public boolean hasCrew(String name) {
        return crews.stream().anyMatch(crew -> crew.isNameMatch(name));
    }

    public Crew getCrew(String name) {
        return crews.stream()
                .filter(crew -> crew.isNameMatch(name))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("크루를 찾을 수 없습니다."));
    }

    public void validateHasCrew(String name) {
        if (!hasCrew(name)) {
            throw new IllegalArgumentException("크루를 찾을 수 없습니다.");
        }
    }
}
