package attendance.fixture;

import attendance.domain.CrewHistory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class TestFixture {

    public static LocalDateTime makeDefaultAttendanceTime() {
        return LocalDateTime.of(2024, 12, 3, 10, 0);
    }

    public static LocalDateTime makeAttendanceTime(int day, int hour, int minute) {
        return LocalDateTime.of(2024, 12, day, hour, minute);
    }

    public static CrewHistory makeCrewHistory(final LocalDateTime... attendanceTimes) {
        Map<LocalDate, LocalDateTime> history = new HashMap<>();
        for (LocalDateTime attendanceTime : attendanceTimes) {
            history.put(LocalDate.from(attendanceTime), attendanceTime);
        }
        return new CrewHistory(history);
    }

    public static LocalDate makeDecemberDate(final int day) {
        return LocalDate.of(2024, 12, day);
    }
}
