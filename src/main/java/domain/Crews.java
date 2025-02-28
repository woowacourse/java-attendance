package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Crews {
    private final List<Crew> crews = new ArrayList<>();

    public Crews() {
    }

    public Crews(List<String> nicknames) {
        nicknames.stream()
                .map(Crew::new)
                .forEach(crews::add);
    }

    public void addCrewIfAbsent(Crew crew) {
        validateNull(crew);
        if (crews.stream()
                .anyMatch(crew::equals)) {
            return;
        }
        crews.add(crew);
    }

    private void validateNull(Crew crew) {
        if (Objects.isNull(crew)) {
            throw new IllegalArgumentException("null이면 안됩니다.");
        }
    }

    public boolean existsByNickname(String nickname) {
        return crews.stream()
                .anyMatch(crew -> nickname.equals(crew.nickname()));
    }

    public List<Crew> findAllCrews() {
        return Collections.unmodifiableList(crews);
    }

    public List<String> findAllCrewNicknames() {
        return findAllCrews().stream()
                .map(Crew::nickname)
                .toList();
    }
}
