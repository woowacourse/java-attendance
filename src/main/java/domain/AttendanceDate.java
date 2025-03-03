package domain;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class AttendanceDate {

    private final LocalDate localDate;

    private AttendanceDate(final int year, final int month, final int date) {
        this.localDate = validateDateFormat(year, month, date);
    }

    public static AttendanceDate of(final int year, final int month, final int date) {
        return new AttendanceDate(year, month, date);
    }

    private LocalDate validateDateFormat(final int year, final int month, final int date) {
        try {
            return LocalDate.of(year, month, date);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("날짜 형식이 잘못 되었습니다.");
        }
    }

    public LocalDateTime createLocalDateTime(final LocalTime localTime) {
        return LocalDateTime.of(getYear(), getMonth(), getDayOfMonth(), localTime.getHour(), localTime.getMinute());
    }

    public LocalDateTime createLocalDateTime(final int date, final LocalTime localTime) {
        return LocalDateTime.of(getYear(), getMonth(), date, localTime.getHour(), localTime.getMinute());
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

    public void possibleAttendance() {
        Calender.validateHolyDay(this.localDate);
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AttendanceDate that = (AttendanceDate) o;
        return Objects.equals(localDate, that.localDate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(localDate);
    }
}
