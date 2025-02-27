package attendance.domain;

import static attendance.util.DateFormatUtil.DATE_FORMATTER;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Objects;

public class WoowaDate {

    private final LocalDate date;
    private final EducationDayPolicy policy;

    public WoowaDate(LocalDate date, EducationDayPolicy policy) {
        this.policy = policy;
        validate(date);
        this.date = date;
    }

    private void validate(LocalDate date) {
        if (!policy.isEducationDay(date)) {
            throw new IllegalArgumentException(date.format(DATE_FORMATTER));
        }
    }

    public LocalDate toLocalDate() {
        return date;
    }

    public DayOfWeek getDayOfWeek() {
        return date.getDayOfWeek();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WoowaDate woowaDate = (WoowaDate) o;
        return Objects.equals(date, woowaDate.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }
}
