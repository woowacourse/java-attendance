package domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Attendance {
    private String name;
    private LocalDate localDate;
    private LocalTime localTime;

    public Attendance(String name, LocalDate localDate, LocalTime localTime) {
        this.name = name;
        this.localDate = localDate;
        this.localTime = localTime;
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
