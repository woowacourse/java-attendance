package attendance.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Crews {
    private final Set<Crew> crews;

    public Crews() {
        this.crews = new HashSet<>();
    }

    public void initCrews(List<List<String>> csvData) {
        csvData.stream()
                .map(List::getFirst)
                .distinct()
                .forEach(uniqueCrewName -> crews.add(new Crew(uniqueCrewName))); // Crew 객체 추가
    }

    public boolean contains(Crew crew) {
        return crews.contains(crew);
    }

    public Crew findCrew(String name) {
        return crews.stream()
                .filter(crew -> crew.isName(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("입력하신 크루가 존재하지 않습니다."));
    }
}
