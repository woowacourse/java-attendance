package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public record Attendances(Map<LocalDate, Attendance> attendances) {

    public Attendances() {
        this(new HashMap<>());
    }

    public void put(LocalDateTime dateTime) {
    }

    public void add(LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();
        attendances.put(date, new Attendance(dateTime));
    }

    public Attendance get(LocalDate date) {
        return attendances.get(date);
    }
}
