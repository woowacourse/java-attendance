package domain;

import util.Dates;

import java.time.LocalDate;
import java.util.Objects;

public class AttendanceDate {
    private final LocalDate attendanceDate;

    public AttendanceDate(LocalDate attendanceDate) {
        validateIsWorkingDay(attendanceDate);
        this.attendanceDate = attendanceDate;
    }

    public LocalDate getDate() {
        return attendanceDate;
    }

    private void validateIsWorkingDay(LocalDate attendanceDate) {
        if (Dates.isHoliday(attendanceDate)) {
            throw new IllegalArgumentException("주말, 공휴일에는 출석할 수 없습니다.");
        }
    }

    public static AttendanceDate of(int year, int month, int day) {
        return new AttendanceDate(LocalDate.of(year, month, day));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttendanceDate that = (AttendanceDate) o;
        return attendanceDate.equals(that.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(attendanceDate);
    }
}
