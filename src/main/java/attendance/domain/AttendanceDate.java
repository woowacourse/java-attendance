package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.MonthDay;
import java.util.Arrays;
import java.util.Objects;

public class AttendanceDate {

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

    public DayOfWeek getDayOfWeek() {
        return attendanceDate.getDayOfWeek();
    }

    public boolean isBeforeAndEqual(AttendanceDate attendanceDate) {
        return this.attendanceDate.isBefore(attendanceDate.attendanceDate) ||
                this.attendanceDate.isEqual(attendanceDate.attendanceDate);
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

    private enum Holiday {
        CHRISTMAS(MonthDay.of(12, 25));

        private final MonthDay monthDay;

        Holiday(MonthDay monthDay) {
            this.monthDay = monthDay;
        }

        public static boolean isHoliday(MonthDay monthDay) {
            return Arrays.stream(values())
                    .anyMatch(holiday -> holiday.monthDay.equals(monthDay));
        }
    }
}
