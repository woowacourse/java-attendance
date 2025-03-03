package domain;

import exception.InvalidDateException;

import java.time.LocalDate;
import java.util.Objects;

public class AttendanceDate {
    private final LocalDate date;

    public AttendanceDate(LocalDate date) {
        validate(date);
        this.date = date;
    }

    public static boolean isValid(LocalDate date) {
        return EducationTime.isOperatingOn(date.getDayOfWeek()) && !Holiday.matches(date);
    }

    public LocalDate getValue() {
        return date;
    }

    private void validate(LocalDate date) {
        if (!isValid(date)) {
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
