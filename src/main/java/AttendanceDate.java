import exception.InvalidDateException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Objects;

public class AttendanceDate {
    private final LocalDate date;

    public AttendanceDate(LocalDate date) {
        validateDate(date);
        this.date = date;
    }

    public LocalDate getValue() {
        return date;
    }

    // TODO: 크리스마스 예외처리
    private void validateDate(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            throw new InvalidDateException();
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AttendanceDate that = (AttendanceDate) object;
        return Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }
}
