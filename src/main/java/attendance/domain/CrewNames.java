package attendance.domain;

import java.util.HashSet;
import java.util.Set;

public class CrewNames {
    private static final String INVALID_CREW_NAME = "[ERROR] 존재하지 않는 크루 이름입니다.\n";

    private final Set<CrewName> crewNames;

    public CrewNames() {
        crewNames = new HashSet<>();
    }

    public void initializeCrewNames(final Set<String> crewNamesData) {
        for (String crewNameData : crewNamesData) {
            crewNames.add(new CrewName(crewNameData));
        }
    }

    public CrewName findCrewName(final String crewNameInput) {
        return crewNames.stream()
                .filter(crewName -> crewName.getCrewName().equals(crewNameInput))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_CREW_NAME));
    }

    public Set<CrewName> getCrewNames() {
        return crewNames;
    }
}
