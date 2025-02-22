package attendance.model;

import static attendance.util.DateFormatUtil.NOT_ATTENDABLE_FORMATTER;

import attendance.util.DateUtil;
import java.time.DayOfWeek;
import java.time.LocalDate;

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

    public LocalDate getLocalDate() {
        return localDate;
    }

    public DayOfWeek getDayOfWeek() {
        return localDate.getDayOfWeek();
    }

}
