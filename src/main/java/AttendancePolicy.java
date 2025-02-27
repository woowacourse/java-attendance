import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.util.Set;

public class AttendancePolicy {
    private static final LocalTime DEFAULT_START_TIME = LocalTime.of(10, 0);
    private static final int LATE_STANDARD = 5;
    private static final int ABSENT_STANDARD = 30;
    private static final LocalTime OPERATING_START_TIME = LocalTime.of(8, 0);
    private static final LocalTime OPERATING_END_TIME = LocalTime.of(23, 0);

    private static final Set<DayOfWeek> WEEKENDS = Set.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
    private static final Set<MonthDay> HOLIDAYS = Set.of(MonthDay.of(12, 25));


    public static AttendanceStatus checkAttendanceStatus(LocalDateTime attendanceTime) {
        if (LocalTime.from(attendanceTime).isBefore(DEFAULT_START_TIME.plusMinutes(LATE_STANDARD))) {
            return AttendanceStatus.ATTEND;
        }
        if (LocalTime.from(attendanceTime).isBefore(DEFAULT_START_TIME.plusMinutes(ABSENT_STANDARD))) {
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.ABSENT;
    }

    public static boolean isOperatingTime(LocalTime attendanceTime) {
        return !attendanceTime.isBefore(OPERATING_START_TIME) && !attendanceTime.isAfter(OPERATING_END_TIME);
    }

    public static boolean isOperatingDay(LocalDateTime attendanceTime) {
        if (WEEKENDS.contains(DayOfWeek.from(attendanceTime)) || HOLIDAYS.contains(MonthDay.from(attendanceTime))) {
            return false;
        }
        return true;
    }

}
