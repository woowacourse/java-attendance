package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Crew {
    private final String name;
    private final Map<LocalDate, LocalTime> attendanceBook;

    public Crew(final String name) {
        this.name = name;
        attendanceBook = new HashMap<>();
    }

    public void addAttendStatus(final LocalDateTime target) {
        LocalDate date = target.toLocalDate();
        LocalTime time = target.toLocalTime();
        attendanceBook.put(date, time);
    }

    public boolean isNameMatch(final String name) {
        return this.name.equals(name);
    }

    public void editAttendStatus(final LocalDateTime target) {
        LocalDate date = target.toLocalDate();
        if (!attendanceBook.containsKey(date)) {
            throw new IllegalArgumentException();
        }
        attendanceBook.put(date, target.toLocalTime());
    }

    public LocalTime getAttendanceTime(final LocalDate date) {
        return attendanceBook.getOrDefault(date, LocalTime.of(0, 0));
    }
}
