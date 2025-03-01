package attendance.fixture;

import attendance.domain.CrewHistory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public class TestFixture {

    public static LocalDateTime makeAttendanceTime() {
        return LocalDateTime.of(2024, 12, 3, 10, 0);
    }

    public static CrewHistory makeCrewHistory(final LocalDateTime attendanceTime) {
        return new CrewHistory(Map.of(LocalDate.from(attendanceTime), attendanceTime));
    }
}
