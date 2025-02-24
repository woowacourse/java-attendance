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
}
