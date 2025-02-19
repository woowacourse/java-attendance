package domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceManager {

    private Map<String, List<CrewRecord>> crews;

    public AttendanceManager() {
        this.crews = new HashMap<>();
    }

    public void createCrew(String name, List<CrewRecord> crewRecords) {
        crews.put(name, crewRecords);
    }
}
