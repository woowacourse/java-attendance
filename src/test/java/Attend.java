import domain.AttendStatus;
import domain.OperationTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Attend {

    private final LocalDate date;
    private final LocalTime time;

    public Attend(final LocalDate date, final LocalTime time) {
        OperationTime.checkIsOperationDate(date);
        this.date = date;
        this.time = time;
    }

    public Attend(final LocalDate date) {
        this(date, null);
    }

    public boolean equalsDay(final int day) {
        return date.getDayOfMonth() == day;
    }

    public boolean equalsDate(final Attend attend) {
        return this.date.equals(attend.date);
    }

    public AttendStatus checkStatus() {
        return AttendStatus.checkAttendStatus(this.date, this.time);
    }

    @Override
    public boolean equals(final Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Attend attend = (Attend) object;
        return Objects.equals(date, attend.date) && Objects.equals(time, attend.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, time);
    }
}
