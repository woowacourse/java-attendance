package attendance.domain;

import static attendance.domain.DayOfWeek.SATURDAY;
import static attendance.domain.DayOfWeek.SUNDAY;
import static attendance.domain.DayOfWeek.findDayOfWeek;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class AttendanceTime {

    private static final LocalTime ABSENCE_TIME = LocalTime.of(23, 59, 59, 999999999);
    private final LocalDateTime attendanceDateTime;

    private AttendanceTime(LocalDateTime attendanceDateTime) {
        ifWeekendThrowException(attendanceDateTime);
        ifNotOperatingThrowException(attendanceDateTime);
        this.attendanceDateTime = attendanceDateTime;
    }

    private AttendanceTime(LocalDate attendanceDate) {
        this.attendanceDateTime = LocalDateTime.of(attendanceDate, ABSENCE_TIME);
    }

    public static AttendanceTime from(LocalDateTime attendanceDateTime) {
        return new AttendanceTime(attendanceDateTime);
    }

    public static AttendanceTime from(LocalDate attendanceDate) {
        return new AttendanceTime(attendanceDate);
    }

    public boolean isSameDate(int findDate) {
        return attendanceDateTime.getDayOfMonth() == findDate;
    }

    public boolean hasDate(LocalDate findDate) {
        return attendanceDateTime.toLocalDate().equals(findDate);
    }

    public boolean isAbsenceTime(LocalTime attendanceTime) {
        return attendanceTime == ABSENCE_TIME;
    }

    public boolean isAbsenceDate() {
        if (attendanceDateTime.toLocalTime() == null) {
            return true;
        }
        return false;
    }

    public LocalDateTime getAttendanceDateTime() {
        return attendanceDateTime;
    }

    public LocalDate getDate() {
        return attendanceDateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return attendanceDateTime.toLocalTime();
    }

    private static void ifWeekendThrowException(LocalDateTime attendanceDateTime) {
        DayOfWeek dayOfWeek = findDayOfWeek(attendanceDateTime.toLocalDate());
        if (dayOfWeek == SATURDAY || dayOfWeek == SUNDAY) {
            throw new IllegalArgumentException("주말에는 등교할 수 없습니다.");
        }
    }

    private static void ifNotOperatingThrowException(LocalDateTime attendanceDateTime) {
        if (!OperatingTime.isOperating(attendanceDateTime.toLocalTime())) {
            throw new IllegalArgumentException("운영시간이 아닙니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AttendanceTime that = (AttendanceTime) o;
        return Objects.equals(attendanceDateTime.toLocalDate(),
            that.attendanceDateTime.toLocalDate());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(attendanceDateTime.toLocalDate());
    }

}
