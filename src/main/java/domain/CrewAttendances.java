package domain;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class CrewAttendances {

    private final Map<String, CrewAttendance> crewAttendances;

    public CrewAttendances(String nickname, LocalTime time) {
        crewAttendances = new HashMap<>();
    }

    public void addAttendance(String nickname, LocalTime time) {
    }

    public CrewAttendanceHistory crewAttendanceHistory(String nickname, LocalTime time) {
        return null;
    }
}
