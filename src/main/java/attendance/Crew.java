package attendance;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Crew {

    private final String name;
    private final Map<LocalDate, LocalTime> attendanceRecords = new HashMap<>();

    public Crew(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void attendance(LocalDate date, LocalTime time) {
        if (attendanceRecords.containsKey(date)) {
            throw new IllegalArgumentException();
        }
        attendanceRecords.put(date, time);
    }
}
