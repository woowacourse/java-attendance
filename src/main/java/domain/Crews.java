package domain;

import java.util.Set;

public class Crews {

    private final Set<Crew> crews;

    public Crews(Set<Crew> crews) {
        this.crews = crews;
    }

    public Crew findByNickname(String nickname) {
        return crews.stream()
                .filter(crew -> crew.isSameNickname(nickname))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("해당 닉네임의 크루가 존재하지 않습니다."));
    }
}
