package domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Attendance {

    LocalDate date;
    LocalTime time;

    public Attendance(LocalDate date, LocalTime time) {
        this.date = date;
        this.time = time;
    }
}
