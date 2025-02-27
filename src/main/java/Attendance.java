import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class Attendance {
    private final AttendanceDate date;
    private final AttendanceTime time;

    public Attendance(LocalDate date, LocalTime time) {
        this.date = new AttendanceDate(date);
        this.time = new AttendanceTime(time);
    }

    public LocalDateTime getDateTime() {
        return LocalDateTime.of(date.getValue(), time.getValue());
    }

    public AttendanceStatus getStatus() {
        if (time.getValue().isAfter(LocalTime.of(10, 5))) {
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.ATTENDANCE;
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
