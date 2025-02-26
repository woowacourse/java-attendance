package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Crew {
    private final String name;
    private final Map<LocalDate, LocalTime> timeLogs;

    public Crew(String name) {
        this.name = name;
        this.timeLogs = new HashMap<>();
    }

    public void addNewTimeLog(LocalDate date, LocalTime time) {
        timeLogs.put(date, time);
    }

    public boolean isMyName(String value) {
        return Objects.equals(name, value);
    }

    public boolean isDateExisted(LocalDate date) {
        return timeLogs.containsKey(date);
    }
}