package domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class Attendance {

    private Crew crew;
    private LocalDateTime time;

    private Attendance(Crew crew, LocalDateTime time) {
        validateDayOfWeek(time);
        this.crew = crew;
        this.time = time;
    }

    public static Attendance of(Crew crew, LocalDateTime time) {
        return new Attendance(crew, time);
    }

    private void validateDayOfWeek(LocalDateTime dateTime) {
        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException();
        }
    }
}
