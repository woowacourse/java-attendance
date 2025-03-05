package model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Objects;
import model.policy.CampusOperatingPolicy;

public class AttendanceDate {

    private final LocalDate date;

    public AttendanceDate(final LocalDate date) {
        validateCampusOperatingDay(date);
        this.date = date;
    }

    public DayOfWeek getDayOfWeek() {
        return date.getDayOfWeek();
    }

    public LocalDate getDate() {
        return date;
    }

    private void validateCampusOperatingDay(final LocalDate date) {
        if (!CampusOperatingPolicy.isOperatingDate(date)) {
            throw new IllegalArgumentException("[ERROR] 등교일이 아닙니다.");
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AttendanceDate that = (AttendanceDate) o;
        return Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(date);
    }
}
