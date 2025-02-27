package attendance.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Holiday {

    private final LocalDate holiday;

    public Holiday(final LocalDate date) {
        this.holiday = date;
    }

    @Override
    public boolean equals(final Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Holiday holiday = (Holiday) object;
        return Objects.equals(this.holiday, holiday.holiday);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(holiday);
    }
}
