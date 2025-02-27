import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Attendance {
    private final LocalDateTime dateTime;

    public Attendance(LocalDate date, LocalTime time) {
        this.dateTime = LocalDateTime.of(date, time);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public AttendanceStatus getStatus() {
        return AttendanceStatus.ATTENDANCE;
    }
}
