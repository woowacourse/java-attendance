package attendance.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Crews {

    private final Set<Crew> crews;

    public Crews(Set<Crew> crews) {
        this.crews = crews;
    }

    public void addCrew(Crew crew) {
        crews.add(crew);
    }

    public Crew getCrew(String nickName) {
        return crews.stream()
            .filter(crew -> crew.getNickName().equals(nickName))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("\n[ERROR] 등록되지 않은 닉네임입니다."));
    }

    public List<Crew> getCrews() {
        return new ArrayList<>(crews);
    }
}
