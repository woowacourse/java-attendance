package domain;

import java.util.List;

public class Crews {
    private List<Crew> crews;

    public Crews(final List<Crew> crews) {
        this.crews = crews;
    }

    public Crew findByNickname(final Nickname nickname) {
        return crews.stream()
                .filter(o -> nickname.equals(o.nickname))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("크루가 존재하지 않습니다."));
    }
}
