package attendance.fixture;

import attendance.domain.CrewHistory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class TestFixture {

    public static LocalDateTime makeDefaultAttendanceTime() {
        return LocalDateTime.of(2024, 12, 3, 10, 0);
    }

    public static LocalDateTime makeDateTime(int day, int hour, int minute) {
        return LocalDateTime.of(2024, 12, day, hour, minute);
    }

    public static LocalDateTime makeAttendanceExceptMonday(int day) {
        return LocalDateTime.of(2024, 12, day, 10, 0);
    }

    public static LocalDateTime makeAttendanceMonday(int day) {
        return LocalDateTime.of(2024, 12, day, 13, 0);
    }

    public static LocalDateTime makeTardinessExceptMonday(int day) {
        return LocalDateTime.of(2024, 12, day, 10, 6);
    }

    public static LocalDateTime makeTardinessMonday(int day) {
        return LocalDateTime.of(2024, 12, day, 13, 6);
    }

    public static LocalDateTime makeAbsenceExceptMonday(int day) {
        return LocalDateTime.of(2024, 12, day, 10, 31);
    }

    public static LocalDateTime makeAbsenceMonday(int day) {
        return LocalDateTime.of(2024, 12, day, 13, 31);
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

    public static LocalTime makeAttendanceTime(final int hour, final int minute) {
        return LocalTime.of(hour, minute);
    }
}
