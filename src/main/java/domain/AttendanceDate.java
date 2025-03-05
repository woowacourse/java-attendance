package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Objects;
import util.DateTimeFormatProvider;

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
        if (ClassDayOff.isDayOff(localDate)) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] %s은 등교일이 아닙니다.", DateTimeFormatProvider.toLocalDateKoreanFormat(localDate)));
        }
    }

    public LocalDate getLocalDate() {
        return localDate;
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
