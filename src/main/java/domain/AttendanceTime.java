package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class AttendanceTime {
    private final LocalDate date;
    private LocalTime time;

    private AttendanceTime(LocalDate date, LocalTime time) {
        validate(date);
        this.date = date;
        this.time = time;
    }

    private void validate(LocalDate date) {
        validateNotWeekend(date);
        validateNotHoliday(date);
    }

    private void validateNotWeekend(LocalDate date) {
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException("주말에는 출석할 수 없습니다.");
        }
    }

    private void validateNotHoliday(LocalDate date) {
        if (Holiday.isHoliday(date)) {
            throw new IllegalArgumentException("공휴일에는 출석할 수 없습니다.");
        }
    }

    public static AttendanceTime of(LocalDate date, LocalTime time) {
        return new AttendanceTime(date, time);
    }

    public AttendanceTime modify(AttendanceTime attendanceTime) {
        AttendanceTime previous = AttendanceTime.of(this.date, this.time);
        this.time = attendanceTime.time;
        return previous;
    }

    public LocalDateTime toLocalDateTime() {
        return LocalDateTime.of(this.date, this.time);
    }

    public boolean isSameDate(AttendanceTime attendanceTime) {
        return this.date.equals(attendanceTime.date);
    }

    public boolean isSameDate(LocalDate date) {
        return this.date.equals(date);
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
