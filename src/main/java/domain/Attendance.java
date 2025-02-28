package domain;

import java.time.LocalDateTime;

public class Attendance {
    private final LocalDateTime dateTime;

    public Attendance(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
