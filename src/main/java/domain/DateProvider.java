package domain;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Objects;

public class DateProvider {

    private final LocalDate localDate;

    private DateProvider(final int year, final int month, final int today) {
        this.localDate = validateFormat(year, month, today);
    }

    public static DateProvider from(final int year, final int month, final int today) {
        return new DateProvider(year, month, today);
    }

    public int getTodayMonth() {
        return this.localDate.getMonthValue();
    }

    public int getToday() {
        return this.localDate.getDayOfMonth();
    }

    private LocalDate validateFormat(final int year, final int month, final int today) {
        try {
            return LocalDate.of(year, month, today);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("날짜 형식이 잘못 되었습니다.");
        }
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
