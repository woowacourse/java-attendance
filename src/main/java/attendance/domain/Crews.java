package attendance.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Crews {
    private static final String NO_SUCH_CREW_ERROR_MESSAGE = "[ERROR] 입력하신 이름의 크루가 존재하지 않습니다.";

    private final List<Crew> crews;

    public Crews() {
        this.crews = new ArrayList<>();
    }

    public void initCrews(final List<List<String>> attendanceRecords) {
        Set<String> uniqueCrewNames = new HashSet<>();

        for (List<String> attendanceRecord : attendanceRecords) {
            uniqueCrewNames.add(attendanceRecord.getFirst());
        }
        for (String uniqueCrewName : uniqueCrewNames) {
            crews.add(new Crew(uniqueCrewName));
        }
    }

    public Crew findCrew(final String crewName) {
        return crews.stream()
                .filter(crew -> crew.isSameCrewName(crewName))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(NO_SUCH_CREW_ERROR_MESSAGE));
    }

    public List<Crew> getCrews() {
        return crews;
    }
}
