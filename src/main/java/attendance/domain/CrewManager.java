 package attendance.domain;

import java.util.ArrayList;
import java.util.List;

public class CrewManager {
    private final List<Crew> crews = new ArrayList<>();

    public void addCrew(Crew crew) {
        crews.add(crew);
    }

    public boolean contains(Crew crew) {
        return crews.contains(crew);
    }

    public Crew findByCrewName(String name){
        return crews.stream()
                .filter(crew -> crew.getName().equals(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 이름을 가진 크루는 없음"));
    }
}
