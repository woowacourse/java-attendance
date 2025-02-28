package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Attendance {
    private final LocalDateTime dateTime;

    public Attendance(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public boolean has(LocalDate day) {
        return this.dateTime.toLocalDate().isEqual(day);
    }
}
