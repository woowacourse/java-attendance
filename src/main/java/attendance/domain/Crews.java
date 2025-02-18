package attendance.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Crews {
    private final List<Crew> crews;

    public Crews() {
        this.crews = new ArrayList<>();
    }

    public void initCrews(List<List<String>> attendanceRecords) {
        List<String> crewNames = new ArrayList<>();

        for (List<String> attendanceRecord : attendanceRecords) {
            crewNames.add(attendanceRecord.getFirst());
        }

        HashSet<String> uniqueCrewNames = new HashSet<>(crewNames);

        for (String uniqueCrewName : uniqueCrewNames) {
            crews.add(new Crew(uniqueCrewName));
        }
    }

    public List<Crew> getCrews() {
        return crews;
    }
}
