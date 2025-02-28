package domain;

import java.util.HashMap;
import java.util.Map;

public class CrewRecords {
    private final Map<Crew, AttendanceRecords> records = new HashMap<>();

    public void addCrewRecords(Crew crew, AttendanceRecords attendanceRecords) {
        records.put(crew, attendanceRecords);
    }

    public void validateCrew(Crew crew) {
        if (!records.containsKey(crew)) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다." + System.lineSeparator());
        }
    }

    public void addRecord(Crew crew, AttendanceRecord record) {
        records.get(crew).add(record);
    }
}
