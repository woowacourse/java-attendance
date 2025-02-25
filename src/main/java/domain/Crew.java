package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Crew {
    private final String name;
    private final Map<LocalDate, LocalTime> attendances = new HashMap<>();

    public Crew(String name) {
        this.name = name;
    }

    public void checkAttendance(LocalDate date, LocalTime time) {
        attendances.put(date, time);
    }

    public Map<LocalDate, LocalTime> getAttendances() {
        return attendances;
    }
}
