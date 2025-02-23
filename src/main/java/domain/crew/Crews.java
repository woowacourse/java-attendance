package domain.crew;

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
        crews.add(Crew.from(nickname));
    }

    public Crew findCrewBy(String nickname) {
        return crews.stream()
                .filter(crew -> crew.hasSame(nickname))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 닉네임입니다."));
    }
}
