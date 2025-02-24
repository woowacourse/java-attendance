package domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class CrewFactory {
    public CrewGroup crewGroupOf(Map<String, List<LocalDateTime>> crewInitAttendanceDates) {
        List<String> crewNames = crewInitAttendanceDates.keySet().stream().toList();
        CrewGroup crewGroup = CrewGroup.from(crewNames);

        for (String crewName : crewNames) {
            Crew crew = crewGroup.findCrew(crewName);
            crew.initializeAttendance(crewInitAttendanceDates.get(crewName));
        }
        return crewGroup;
    }
}
