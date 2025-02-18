import java.time.LocalDate;
import java.time.LocalDateTime;

public class Attendance {
    private final Crew crew;
    private final LocalDateTime time;

    public Attendance(Crew crew, LocalDateTime time) {
        this.crew = crew;
        this.time = time;
    }

    public boolean isSameDateWith(LocalDateTime dateTime) {
        return dateTime.toLocalDate().isEqual(time.toLocalDate());
    }

    public Crew getCrew() {
        return crew;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Attendance other = (Attendance) obj;
        return crew.equals(other.crew) && time.isEqual(other.time);
    }
}
