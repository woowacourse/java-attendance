import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Attendance {
    private final AttendanceDate date;
    private final AttendanceTime time;
    private final AttendanceStatus status;

    public Attendance(LocalDate date, LocalTime time) {
        this.date = new AttendanceDate(date);
        this.time = new AttendanceTime(time);
        LocalTime startTime = EducationTime.startOf(date.getDayOfWeek());
        this.status = AttendanceStatus.from(startTime, time);
    }

    public boolean isAttendedOn(LocalDate targetDate) {
        return date.equals(new AttendanceDate(targetDate));
    }

    public LocalTime getTime() {
        return time.getValue();
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Attendance that = (Attendance) object;
        return Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }
}
