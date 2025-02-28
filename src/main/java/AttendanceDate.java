import exception.InvalidDateException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
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

    private void validateDate(LocalDate date) {
        DayOfWeek attendanceDay = date.getDayOfWeek();
        if (!EducationTime.isOperatingOn(attendanceDay)) {
            throw new InvalidDateException();
        }
        if (date.getDayOfMonth() == 25 && date.getMonth() == Month.DECEMBER) {
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
