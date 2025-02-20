package attendance.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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

    public Stream<Crew> stream() {
        return crews.stream();
    }

    public Crew findCrew(String name) {
        return crews.stream()
                .filter(crew -> crew.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 닉네임입니다."));
    }

    public boolean containsCrew(String name) {
        return crews.stream()
                .anyMatch(crew -> crew.getName().equals(name));
    }

}
