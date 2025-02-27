package domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Attendance {
    private final LocalDate localDate;
    private final LocalTime localTime;

    public Attendance(LocalDate localDate, LocalTime localTime) {
        this.localDate = localDate;
        this.localTime = localTime;
    }

    public Attendance updateTime(LocalTime newTime) {
        return new Attendance(localDate, newTime);
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

}
