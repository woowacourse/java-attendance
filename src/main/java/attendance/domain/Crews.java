package attendance.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Crews {
    private final HashSet<Crew> crews;

    public Crews() {
        this.crews = new HashSet<>();
    }

    public void initCrews(final List<List<String>> attendanceRecords) {
        List<String> crewNames = new ArrayList<>();

        for (List<String> attendanceRecord : attendanceRecords) {
            crewNames.add(attendanceRecord.getFirst());
        }

        HashSet<String> uniqueCrewNames = new HashSet<>(crewNames);

        for (String uniqueCrewName : uniqueCrewNames) {
            crews.add(new Crew(uniqueCrewName));
        }
    }

    public Crew findCrew(final String crewName) {
        return crews.stream()
                .filter(crew -> crew.isSameCrewName(crewName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 입력하신 이름의 크루가 존재하지 않습니다."));
    }

    public HashSet<Crew> getCrews() {
        return crews;
    }
}
