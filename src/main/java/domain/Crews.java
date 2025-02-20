package domain;

import java.util.HashSet;
import java.util.Set;

public class Crews {
    private final Set<Crew> crews;

    public Crews() {
        crews = new HashSet<>();
    }

    public Set<Crew> getCrews() {
        return new HashSet<>(crews);
    }

    public void add(String nickname) {
        crews.add(Crew.of(nickname));
    }

    public Crew findCrewBy(String nickname) {
        return crews.stream()
                .filter(crew -> crew.hasSame(nickname))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 크루입니다"));
    }
}
