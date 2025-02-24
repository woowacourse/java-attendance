package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Attendance {
    private final LocalDate date;
    private final LocalTime time;

    public Attendance(LocalDate date, LocalTime time) {
        this.date = date;
        this.time = time;
    }
}
