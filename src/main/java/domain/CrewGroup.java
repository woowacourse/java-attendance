package domain;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CrewGroup {
    private List<Crew> crews;

    private CrewGroup(List<Crew> crews) {
        this.crews = crews;
    }

    public static CrewGroup from(List<String> crewNames) {
        if (crewNames.stream().distinct().count() != crewNames.size()) {
            throw new IllegalArgumentException("중복된 이름의 크루는 존재할 수 없습니다.");
        }

        List<Crew> crews = crewNames.stream().map(crewName -> new Crew(crewName)).toList();
        return new CrewGroup(crews);
    }

    public Crew findCrew(String crewName) {
        Optional<Crew> findCrew = crews.stream().filter(crew -> crew.getName().equals(crewName)).findFirst();
        if (findCrew.isEmpty()) {
            throw new IllegalArgumentException("등록되지 않은 닉네임입니다.");
        }
        return findCrew.get();
    }

    public List<Crew> sortedAttendanceWarning() {
        return crews.stream()
                .filter(Crew::isAttendanceWarning)
                .sorted()
                .collect(Collectors.toList());
    }
}
