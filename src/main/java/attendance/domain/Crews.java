package attendance.domain;

import java.util.ArrayList;
import java.util.List;

public class Crews {

    private final List<Crew> crews = new ArrayList<>();

    public void add(Crew crew) {
        crews.add(crew);
    }

    public Crew get(String name) {
        return crews.stream()
            .filter(crew -> crew.getName().equals(name))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 크루입니다."));
    }
}
