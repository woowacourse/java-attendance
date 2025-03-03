package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Crews {
    
    private final List<Crew> crews = new ArrayList<>();

    public Crews() {
    }

    public Crews(List<Crew> crews) {
        this.crews.addAll(crews);
    }

    public void addCrewIfAbsent(Crew crew) {
        validateNull(crew);
        if (crews.stream()
                .anyMatch(crew::equals)) {
            return;
        }
        crews.add(crew);
    }

    public boolean existsByNickname(String nickname) {
        return crews.stream()
                .anyMatch(crew -> nickname.equals(crew.getNickname()));
    }

    public Crew findByNickname(String nickname) {
        return crews.stream()
                .filter(crew -> nickname.equals(crew.getNickname()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(nickname + ": 존재하지 않는 크루입니다."));
    }

    public List<Crew> findAllCrews() {
        return Collections.unmodifiableList(crews);
    }

    private void validateNull(Crew crew) {
        if (Objects.isNull(crew)) {
            throw new IllegalArgumentException("null이면 안됩니다.");
        }
    }
}
