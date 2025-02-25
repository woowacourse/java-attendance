package domain;

import java.util.List;

public class CrewGroup {
    private List<Crew> crews;

    public CrewGroup(List<String> crewNames) {
        if (crewNames.stream().distinct().count() != crewNames.size()) {
            throw new IllegalArgumentException("중복된 이름의 크루는 존재할 수 없습니다.");
        }
        this.crews = crewNames.stream()
                .map(Crew::new)
                .toList();
    }

    public Crew findCrew(String crewName) {
        return crews.stream()
                .filter(crew -> crew.getName().equals(crewName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 닉네임입니다."));
    }

    public List<Crew> sortedAttendanceWarning() {
        return crews.stream()
                .filter(Crew::isAttendanceWarning)
                .sorted()
                .toList();
    }
}
