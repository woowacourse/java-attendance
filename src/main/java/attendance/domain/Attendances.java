package attendance.domain;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Attendances {
    private final Map<LocalDate, Attendance> attendances;

    public Attendances() {
        this.attendances = new HashMap<>();
    }

    public void addAttendance(LocalDate date, LocalTime time) {
        attendances.put(date, new Attendance(date, time));
    }

    public boolean isAttendedDate(LocalDate attendDate) {
        return attendances.containsKey(attendDate);
    }

    public Map<LocalDate, Attendance> getAttendances() {
        return new HashMap<>(attendances);
    }

    public Attendance getCurrentAttendance(LocalDate date) {
        return attendances.get(date);
    }
}
