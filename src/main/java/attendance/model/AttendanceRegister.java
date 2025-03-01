package attendance.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

public class AttendanceRegister {
    private final Map<String, AttendanceRecord> register = new HashMap<>();

    public AttendanceRecord findAttendanceRecordByName(String crewName) {
        validateContainsCrewName(crewName);
        return register.get(crewName);
    }

    public void addNewCrew(String crewName) {
        register.putIfAbsent(crewName, new AttendanceRecord());
    }

    public Stream<Entry<String, AttendanceRecord>> entryStream() {
        return register.entrySet().stream();
    }

    private void validateContainsCrewName(String name) {
        if (!register.containsKey(name)) {
            throw new IllegalArgumentException("존재하지 않는 크루입니다.");
        }
    }
}
