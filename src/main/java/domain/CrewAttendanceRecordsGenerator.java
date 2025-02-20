package domain;

import java.util.Map;

public interface CrewAttendanceRecordsGenerator {
    Map<Crew, AttendanceRecords> generate(DateGenerator dateGenerator);
}
