package domain.attendance;

import domain.holiday.Holiday;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class AttendanceTime implements Comparable<AttendanceTime> {

    private static final LocalTime openTime = LocalTime.of(8, 0);
    private static final LocalTime closeTime = LocalTime.of(23, 0);

    private final LocalDate date;
    private LocalTime time;

    private AttendanceTime(LocalDate date, LocalTime time) {
        validate(date, time);
        this.date = date;
        this.time = time;
    }

    private void validate(LocalDate date, LocalTime time) {
        validateCampusOpen(time);
        validateNotWeekend(date);
        validateNotHoliday(date);
    }

    private void validateCampusOpen(LocalTime time) {
        if (time.isBefore(openTime) || time.isAfter(closeTime)) {
            throw new IllegalArgumentException("캠퍼스 운영 시간이 아닙니다.");
        }
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

    public boolean isLate() {
        int minutes = AttendanceSchedule.calculateLateMinutes(this.date, this.time);
        return minutes > 5 && minutes <= 30;
    }

    public boolean isAbsence() {
        int minutes = AttendanceSchedule.calculateLateMinutes(this.date, this.time);
        return minutes > 30;
    }

    public AttendanceStatus toAttendanceStatus() {
        return AttendanceStatus.from(this);
    }

    public LocalDate toLocalDate() {
        return date;
    }

    public LocalTime toLocalTime() {
        return time;
    }

    public boolean isSameDate(AttendanceTime attendanceTime) {
        return this.date.equals(attendanceTime.date);
    }

    public boolean isSameDate(LocalDate date) {
        return this.date.equals(date);
    }

    public boolean isBefore(LocalDate date) {
        return this.date.isBefore(date);
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

    @Override
    public int compareTo(AttendanceTime o) {
        if (this.date.isEqual(o.date)) {
            return this.time.compareTo(o.time);
        }
        return this.date.compareTo(o.date);
    }
}
