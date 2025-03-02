import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.util.Set;

public class Attendance {
    private static final LocalTime DEFAULT_START_TIME = LocalTime.of(10, 0);
    private static final LocalTime MONDAY_START_TIME = LocalTime.of(13, 0);
    private static final int LATE_STANDARD = 5;
    private static final int ABSENT_STANDARD = 30;
    private static final LocalTime OPERATING_START_TIME = LocalTime.of(8, 0);
    private static final LocalTime OPERATING_END_TIME = LocalTime.of(23, 0);


    private static final Set<DayOfWeek> WEEKENDS = Set.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
    private static final Set<MonthDay> HOLIDAYS = Set.of(MonthDay.of(12, 25));

    private final LocalDateTime attendanceTime;
    private final AttendanceStatus attendanceStatus;

    public Attendance(LocalDateTime attendanceTime) {
        validateOperatingDay(attendanceTime);
        validateOperatingTime(LocalTime.from(attendanceTime));
        this.attendanceTime = attendanceTime;
        this.attendanceStatus = checkAttendanceStatus(attendanceTime);
    }


    public AttendanceStatus checkAttendanceStatus(LocalDateTime attendanceTime) {
        LocalTime startTime = DEFAULT_START_TIME; // 기본 시작 시간
        if (attendanceTime.getDayOfWeek() == DayOfWeek.MONDAY) {
            startTime = MONDAY_START_TIME; // 월요일 시작 시간 적용
        }
        if (LocalTime.from(attendanceTime).isBefore(startTime.plusMinutes(LATE_STANDARD))) {
            return AttendanceStatus.ATTEND;
        }
        if (LocalTime.from(attendanceTime).isBefore(startTime.plusMinutes(ABSENT_STANDARD))) {
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.ABSENT;
    }

    private void validateOperatingTime(LocalTime attendanceTime) {
        if (attendanceTime.isBefore(OPERATING_START_TIME) || attendanceTime.isAfter(OPERATING_END_TIME)) {
            throw new IllegalArgumentException("운영시간이 아닙니다.");
        }

    }

    private void validateOperatingDay(LocalDateTime attendanceTime) {
        if (WEEKENDS.contains(DayOfWeek.from(attendanceTime)) || HOLIDAYS.contains(MonthDay.from(attendanceTime))) {
            throw new IllegalArgumentException("운영일이 아닙니다.");
        }

    }


    public LocalDateTime getAttendanceTime() {
        return attendanceTime;
    }

    public AttendanceStatus getAttendanceStatus() {
        return attendanceStatus;
    }
}
