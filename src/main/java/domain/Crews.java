package domain;

import java.util.Optional;
import java.util.Set;

public class Crews {

    private final Set<Crew> crews;

    public Crews(Set<Crew> crews) {
        this.crews = crews;
    }

    public Optional<Crew> findByNickname(String nickname) {
        return crews.stream()
                .filter(crew -> crew.isSameNickname(nickname))
                .findFirst();
    }
}
