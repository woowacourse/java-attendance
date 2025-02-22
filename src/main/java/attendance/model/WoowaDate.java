package attendance.model;

import static attendance.util.DateFormatUtil.NOT_ATTENDABLE_FORMATTER;

import attendance.util.DateUtil;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Objects;

public class WoowaDate {
    private final LocalDate localDate;
    private final FixedCustomClock fixedCustomClock;

    public WoowaDate(LocalDate localDate, FixedCustomClock fixedCustomClock) {
        this.localDate = localDate;
        this.fixedCustomClock = fixedCustomClock;
        validateWeekendOrHoliday();
    }

    private void validateWeekendOrHoliday() {
        if (DateUtil.isWeekend(localDate) || fixedCustomClock.isHoliday(localDate)) {
            throw new IllegalArgumentException(localDate.format(NOT_ATTENDABLE_FORMATTER));
        }
    }

    public boolean isBefore(WoowaDate other) {
        return this.localDate.isBefore(other.localDate);
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public DayOfWeek getDayOfWeek() {
        return localDate.getDayOfWeek();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WoowaDate woowaDate)) {
            return false;
        }
        return Objects.equals(localDate, woowaDate.localDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(localDate);
    }
}
