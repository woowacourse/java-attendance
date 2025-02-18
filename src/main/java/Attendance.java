import java.time.LocalDateTime;
import java.util.List;

public class Attendance {

    private final Crew crew;
    private List<LocalDateTime> dateTimes;

    public Attendance(final Crew crew, final List<LocalDateTime> dateTimes) {
        this.crew = crew;
        this.dateTimes = dateTimes;
    }

    public List<LocalDateTime> getDateTimes() {
        return dateTimes;
    }

    public Crew getCrew() {
        return crew;
    }
}
