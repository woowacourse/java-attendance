package model;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Crews {

    private final List<Crew> crews;

    private Crews(List<Crew> crews) {
        this.crews = crews;
    }

    public static Crews of(List<Crew> crews) {
        return new Crews(crews);
    }

    public Optional<Crew> findByNickname(String nickname) {
        return crews.stream()
                .filter(crew -> crew.isEqualName(nickname))
                .findFirst();
    }

    public void add(Crew crew) {
        validateExistCrew(crew);
        crews.add(crew);
    }

    private void validateExistCrew(Crew crew) {
        if (!crews.contains(crew)) {
            throw new IllegalArgumentException("존재하지 않는 크루입니다.");
        }
    }

    public List<Crew> getCrews() {
        return Collections.unmodifiableList(crews);
    }
}
