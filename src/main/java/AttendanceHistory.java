import java.time.LocalDate;
import java.time.LocalDateTime;

public class AttendanceHistory {
    private final Crew crew;
    //TODO: LocalDateTime 을 래핑해볼까?
    private final LocalDateTime attendAt;

    public AttendanceHistory(Crew crew, LocalDateTime attendAt) {

        AttendanceTimeChecker.checkTime(attendAt.toLocalTime());
        AttendanceTimeChecker.checkDate(attendAt.toLocalDate());

        this.crew = crew;
        this.attendAt = attendAt;
    }

    public boolean isAboutSameCrew(Crew comparedCrew) {
        return crew.equals(comparedCrew);
    }

    public boolean isAboutSameDate(LocalDate requestedDate) {
        return attendAt.getMonth().equals(requestedDate.getMonth())
                && attendAt.getDayOfMonth() == requestedDate.getDayOfMonth();
    }

    public boolean isBefore(LocalDate requestedDate) {
        return attendAt.toLocalDate().isBefore(requestedDate);
    }

    public LocalDateTime getAttendAt() {
        return attendAt;
    }

    public Crew getCrew() {
        return crew;
    }
}
