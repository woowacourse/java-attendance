import java.time.LocalDateTime;

public class AttendancePolicy {

    public static AttendanceStatus checkAttendanceStatus(LocalDateTime attendanceTime) {
        return AttendanceStatus.ATTEND;

    }
}
