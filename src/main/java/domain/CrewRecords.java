package domain;

import java.util.HashMap;
import java.util.Map;

public class CrewRecords {
    private final Map<Crew, AttendanceRecords> records = new HashMap<>();

    public void addCrewRecords(Crew crew, AttendanceRecords attendanceRecords) {
        records.put(crew, attendanceRecords);
    }
}
