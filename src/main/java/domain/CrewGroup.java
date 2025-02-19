package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CrewGroup {
    private List<Crew> crews;

    private CrewGroup(List<Crew> crews) {
        this.crews = crews;
    }

    public static CrewGroup from(List<String> crewNames) {
        if (crewNames.stream().distinct().count() != crewNames.size()) {
            throw new IllegalArgumentException("");
        }

        List<Crew> crews = crewNames.stream().map(crewName -> new Crew(crewName)).toList();
        return new CrewGroup(crews);
    }

//    private boolean has(String crewName) {
//        return this.crews.contains(crewName);
//    }

    public Crew findCrew(String crewName) {
        Optional<Crew> findCrew = crews.stream().filter(crew -> crew.getName().equals(crewName)).findFirst();
        if (findCrew.isEmpty()) {
            throw new IllegalArgumentException("");
        }
        return findCrew.get();
    }
}
