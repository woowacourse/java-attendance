package service;

import domain.Crew;
import domain.CrewGroup;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class CrewService {
    public CrewGroup createCrewGroup(Map<String, List<LocalDateTime>> crewInitAttendanceDates) {
        List<String> crewNames = crewInitAttendanceDates.keySet().stream().toList();
        CrewGroup crewGroup = CrewGroup.from(crewNames);

        for (String crewName : crewNames) {
            Crew crew = crewGroup.findCrew(crewName);
            crew.initializeAttendance(crewInitAttendanceDates.get(crewName));
        }
        return crewGroup;
    }
}
