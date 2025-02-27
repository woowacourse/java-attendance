package domain;

import java.util.HashMap;
import java.util.Map;

public class AttendanceBook {

    private final Map<Crew, AttendanceRecords> crewRecords = new HashMap<>();

    public void attend(Crew crew, AttendanceDateTime attendDateTime) {
        crewRecords.putIfAbsent(crew, new AttendanceRecords());
        AttendanceRecords records = crewRecords.get(crew);
        records.add(attendDateTime);
    }

    public AttendanceRecords getRecordsOfCrew(Crew crew) {
        return crewRecords.getOrDefault(crew, new AttendanceRecords());
    }
}
