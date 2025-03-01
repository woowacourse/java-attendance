package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Objects;

public class AttendanceDate {

    private final LocalDate localDate;

    public AttendanceDate(final LocalDate localDate) {
        validateDate(localDate);
        this.localDate = localDate;
    }

    public DayOfWeek getDayOfWeek() {
        return localDate.getDayOfWeek();
    }

    private void validateDate(final LocalDate localDate) {
        if (CampusHoliday.isDayOff(localDate)) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof final AttendanceDate that)) {
            return false;
        }
        return Objects.equals(localDate, that.localDate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(localDate);
    }
}
