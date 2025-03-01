package domain;

import java.time.LocalDate;
import java.time.LocalTime;
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

    public void updateRecord(Crew crew, LocalDate oldDate, LocalTime newTime) {
        records.get(crew).update(oldDate, newTime);
    }

    public AttendanceRecord getRecordOnDate(Crew crew, LocalDate date) {
        return records.get(crew).getRecordOnDate(date);
    }

    public Map<Crew, AttendanceRecords> getRecords() {
        return records;
    }
}
