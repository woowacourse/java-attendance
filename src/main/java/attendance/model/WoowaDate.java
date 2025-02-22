package attendance.model;

import static attendance.util.DateFormatUtil.NOT_ATTENDABLE_FORMATTER;

import attendance.util.DateUtil;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class WoowaDate {
    private final LocalDate localDate;
    private final CustomClock clock;

    public WoowaDate(LocalDate localDate, CustomClock clock) {
        this.localDate = localDate;
        this.clock = clock;
        validateWeekendOrHoliday();
    }

    private void validateWeekendOrHoliday() {
        if (DateUtil.isWeekendOrHoliday(localDate, clock)) {
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
