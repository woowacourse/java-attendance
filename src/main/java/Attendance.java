import java.time.LocalDate;
import java.time.LocalTime;

public interface Attendance {
    boolean isAttendedOn(LocalDate date);
    boolean isTimeRecorded();
    LocalTime getTime();
    AttendanceStatus getStatus();
}
