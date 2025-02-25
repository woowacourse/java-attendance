package domain;

import java.time.LocalDateTime;

public class AttendanceHistory {
    private final Crew crew;
    private final LocalDateTime dateTime;

    private AttendanceHistory(Crew crew, LocalDateTime dateTime) {
        this.crew = crew;
        this.dateTime = dateTime;
    }

    public static AttendanceHistory of(Crew crew, LocalDateTime dateTime) {
        return new AttendanceHistory(crew, dateTime);
    }
}
