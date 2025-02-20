package domain;

import java.util.Map;

public interface CrewAttendanceRecordsGenerator {
    public Map<Crew, AttendanceRecords> generate(DateGenerator dateGenerator);
}
