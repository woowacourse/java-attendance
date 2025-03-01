package model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class AttendanceDateTime {
    private final LocalDateTime attendanceDateTime;
    private static final LocalDate CHRISTMAS = LocalDate.of(2024, 12, 25);

    public AttendanceDateTime(LocalDateTime attendanceDateTime) {
        this.attendanceDateTime = attendanceDateTime;
    }

    public LocalDate toLocalDate() {
        return attendanceDateTime.toLocalDate();
    }

    public LocalTime toLocalTime() {
        return attendanceDateTime.toLocalTime();
    }

    public DayOfWeek toDayOfWeek() {
        return attendanceDateTime.getDayOfWeek();
    }

    public boolean isWeekend() {
        return (this.toDayOfWeek().equals(DayOfWeek.SATURDAY) || this.toDayOfWeek().equals(DayOfWeek.SUNDAY));
    }

    public boolean isChristmas() {
        return (this.toLocalDate().equals(CHRISTMAS));
    }

    public boolean isNotOpeningTime() {
        return attendanceDateTime.toLocalTime().isBefore(LocalTime.of(8, 0)) || attendanceDateTime.toLocalTime().isAfter(LocalTime.of(23, 0));
    }

    public boolean isSameDate(AttendanceDateTime attendanceDateTime) {
        return attendanceDateTime.toLocalDate().equals(this.attendanceDateTime.toLocalDate());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttendanceDateTime that = (AttendanceDateTime) o;
        return Objects.equals(attendanceDateTime, that.attendanceDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attendanceDateTime);
    }

}
