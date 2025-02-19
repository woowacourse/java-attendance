package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Attendance {

    private final Crew crew;
    private final LocalDateTime checkInTime;

    private Attendance(Crew crew, LocalDateTime checkInTime) {
        this.crew = crew;
        this.checkInTime = checkInTime;
    }

    public static Attendance of(Crew crew, LocalDateTime checkInTime) {
        validateHolidayAndWeekend(checkInTime);

        return new Attendance(crew, checkInTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Attendance that = (Attendance) o;
        return Objects.equals(crew, that.crew) && Objects.equals(checkInTime, that.checkInTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(crew, checkInTime);
    }

    public Crew getCrew() {
        return crew;
    }

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }
}
