package crew;

import java.util.Collections;
import java.util.List;

public class Crews {
    private final List<Crew> crews;

    public Crews(List<Crew> crews) {
        this.crews = crews;
    }

    public Crew findCrewByName(String name) {
        return crews.stream()
                .filter(crew -> crew.hasSameName(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 닉네임입니다."));
    }

    public List<Crew> getAll() {
        return Collections.unmodifiableList(crews);
    }

    public void add(Crew crew) {
        crews.add(crew);
    }
}

