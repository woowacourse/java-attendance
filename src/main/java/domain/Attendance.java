package domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Attendance {
    private final String name;
    private final LocalDate localDate;
    private LocalTime localTime;

    public Attendance(String name, LocalDate localDate, LocalTime localTime) {
        this.name = name;
        this.localDate = localDate;
        this.localTime = localTime;
    }

    public Attendance updateTime(LocalTime newTime) {
        return new Attendance(name, localDate, newTime);
    }

    public String getName() {
        return name;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

}
