import java.time.LocalDateTime;
import java.time.LocalTime;

public class AttendancePolicy {
    private static final LocalTime DEFAULT_START_TIME = LocalTime.of(10, 0);
    private static final int LATE_STANDARD = 5;
    private static final int ABSENT_STANDARD = 30;

    public static AttendanceStatus checkAttendanceStatus(LocalDateTime attendanceTime) {
        if (LocalTime.from(attendanceTime).isBefore(DEFAULT_START_TIME.plusMinutes(LATE_STANDARD))) {
            return AttendanceStatus.ATTEND;
        }
        if (LocalTime.from(attendanceTime).isBefore(DEFAULT_START_TIME.plusMinutes(ABSENT_STANDARD))) {
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.ABSENT;
    }
}
