import java.time.LocalDateTime;

public class Attendance {
    private final Crew crew;
    private final LocalDateTime time;

    public Attendance(Crew crew, LocalDateTime time) {
        this.crew = crew;
        this.time = time;
    }
}
