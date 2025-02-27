package attendance.fixture;

import static attendance.domain.model.AttendanceStatus.DEFAULT_TIME;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TestFixture {

    public static LocalDateTime makeDefaultTime(final int day) {
        return LocalDateTime.of(makeDay(day), DEFAULT_TIME);
    }

    public static LocalDate makeDay(final int day) {
        return LocalDate.of(2024, 12, day);
    }

    public static LocalDateTime makeAttendance(final int day) {
        return LocalDateTime.of(2024, 12, day, 9, 30);
    }

    public static LocalDateTime makeLate(final int day) {
        return LocalDateTime.of(2024, 12, day, 10, 6);
    }

    public static LocalDateTime makeAbsent(final int day) {
        return LocalDateTime.of(2024, 12, day, 10, 31);
    }
}
