package domain;

import java.time.LocalDate;
import java.util.Map;

public interface CrewAttendanceRecordsGenerator {
    Map<Crew, AttendanceRecords> generate(LocalDate currentDate);
}
