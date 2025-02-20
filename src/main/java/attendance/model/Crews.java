package attendance.model;

import java.util.ArrayList;
import java.util.List;

public class Crews {
    private final List<Crew> crews;

    public Crews() {
        this.crews = new ArrayList<>();
    }

    public void add(Crew crew) {
        crews.add(crew);
    }

    public List<Crew> getCrews() {
        return crews;
    }

    public Crew findCrew(Crew crew) {
        return crews.stream()
                .filter(c -> c.getName().equals(crew.getName()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 닉네임입니다."));
    }

    public boolean containsCrew(String name) {
        return crews.stream()
                .anyMatch(c -> c.getName().equals(name));
    }

}
