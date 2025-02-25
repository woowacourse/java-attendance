package domain;

import java.time.LocalDate;

public interface CrewAttendanceRecordsGenerator {
    CrewAttendanceRecords generate(LocalDate currentDate);
}
