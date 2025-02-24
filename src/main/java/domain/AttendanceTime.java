package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class AttendanceTime {
    private final LocalDate date;
    private LocalTime time;

    private AttendanceTime(LocalDate date, LocalTime time) {
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException();
        }
        if (date.getDayOfMonth() == 25) {
            throw new IllegalArgumentException();
        }
        this.date = date;
        this.time = time;
    }

    public static AttendanceTime of(LocalDate date, LocalTime time) {
        return new AttendanceTime(date, time);
    }

    public void modify(LocalTime time) {
        this.time = time;
    }

    public boolean isSameDate(LocalDate date) {
        if (this.date.equals(date)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AttendanceTime that)) {
            return false;
        }
        return (Objects.equals(date, that.date)
                && Objects.equals(time, that.time));
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, time);
    }
}
