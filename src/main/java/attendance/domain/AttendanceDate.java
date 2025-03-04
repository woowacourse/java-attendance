package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.MonthDay;
import java.util.Objects;

public class AttendanceDate {

    public static final AttendanceDate ATTENDANCE_START_DATE = new AttendanceDate(LocalDate.of(2024, 12, 2));

    private final LocalDate attendanceDate;

    public AttendanceDate(LocalDate attendanceDate) {
        validateAttendanceDate(attendanceDate);
        this.attendanceDate = attendanceDate;
    }

    private void validateAttendanceDate(LocalDate attendanceDate) {
        if (isWeekend(attendanceDate)) {
            throw new IllegalArgumentException("주말에는 출석할 수 없습니다.");
        }
        if (Holiday.isHoliday(MonthDay.from(attendanceDate))) {
            throw new IllegalArgumentException("공휴일에는 출석할 수 없습니다.");
        }
    }

    private boolean isWeekend(LocalDate attendanceDate) {
        return attendanceDate.getDayOfWeek() == DayOfWeek.SATURDAY ||
                attendanceDate.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    public AttendanceDate nextDate() {
        LocalDate nextDate = attendanceDate;
        do {
            nextDate = nextDate.plusDays(1);
        } while (!canAttend(nextDate));

        return new AttendanceDate(nextDate);
    }

    private boolean canAttend(LocalDate date) {
        if (isWeekend(date)) {
            return false;
        }
        if (Holiday.isHoliday(MonthDay.from(date))) {
            return false;
        }
        return true;
    }

    public boolean isBeforeAndEqual(LocalDate date) {
        return this.attendanceDate.isBefore(date) ||
                this.attendanceDate.isEqual(date);
    }

    public DayOfWeek getDayOfWeek() {
        return attendanceDate.getDayOfWeek();
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        AttendanceDate that = (AttendanceDate) object;
        return Objects.equals(attendanceDate, that.attendanceDate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(attendanceDate);
    }
}
