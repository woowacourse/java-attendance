package attendance;

import static attendance.DayOfWeek.SATURDAY;
import static attendance.DayOfWeek.SUNDAY;
import static attendance.DayOfWeek.findDayOfWeek;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AttendanceTime {

    private final LocalDateTime attendanceDateTime;

    private AttendanceTime(LocalDateTime attendanceDateTime) {
        ifWeekendThrowException(attendanceDateTime);
        ifNotOperatingThrowException(attendanceDateTime);
        this.attendanceDateTime = attendanceDateTime;
    }

    public static AttendanceTime from(LocalDateTime attendanceDateTime) {
        return new AttendanceTime(attendanceDateTime);
    }

    private static void ifNotOperatingThrowException(LocalDateTime attendanceDateTime) {
        if (!OperatingTime.isOperating(attendanceDateTime.toLocalTime())) {
            throw new IllegalArgumentException("운영시간이 아닙니다.");
        }
    }

    private static void ifWeekendThrowException(LocalDateTime attendanceDateTime) {
        DayOfWeek dayOfWeek = findDayOfWeek(attendanceDateTime.toLocalDate());
        if (dayOfWeek == SATURDAY || dayOfWeek == SUNDAY) {
            throw new IllegalArgumentException("주말에는 등교할 수 없습니다.");
        }
    }

    public LocalDateTime getAttendanceDateTime() {
        return attendanceDateTime;
    }
    public LocalDate getDate() {
        return attendanceDateTime.toLocalDate();
    }
}
