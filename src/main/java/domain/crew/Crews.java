package domain.crew;

import java.util.List;

public class Crews {

    private final List<Crew> crews;

    public Crews(final List<Crew> crews) {
        this.crews = crews;
    }

    public Crew findByNickname(final Nickname nickname) {
        return crews.stream()
                .filter(crew -> crew.isSameAs(nickname))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 크루입니다."));

    }

    public List<Crew> findSortedDisciplinaryCrews() {
        return crews.stream()
                .sorted()
                .toList();
    }
}
