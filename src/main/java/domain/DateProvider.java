package domain;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class DateProvider {

    private final LocalDate localDate;

    private DateProvider(final int year, final int month, final int date) {
        this.localDate = validateDateFormat(year, month, date);
    }

    public static DateProvider of(final int year, final int month, final int date) {
        return new DateProvider(year, month, date);
    }

    private LocalDate validateDateFormat(final int year, final int month, final int date) {
        try {
            return LocalDate.of(year, month, date);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("날짜 형식이 잘못 되었습니다.");
        }
    }

    public int getYear() {
        return this.localDate.getYear();
    }

    public int getMonth() {
        return this.localDate.getMonthValue();
    }

    public int getDayOfMonth() {
        return this.localDate.getDayOfMonth();
    }

    public Calender getDayOfWeek() {
        return Calender.findBy(localDate);
    }

    public LocalDateTime createLocalDateTime(final LocalTime localTime) {
        return LocalDateTime.of(getYear(), getMonth(), getDayOfMonth(), localTime.getHour(), localTime.getMinute());
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DateProvider that = (DateProvider) o;
        return Objects.equals(localDate, that.localDate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(localDate);
    }
}
