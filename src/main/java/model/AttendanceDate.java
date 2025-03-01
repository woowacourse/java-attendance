package model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Objects;

public class AttendanceDate {
    private final LocalDate attendanceDate;
    private static final LocalDate CHRISTMAS = LocalDate.of(2024, 12, 25);

    public AttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public DayOfWeek toDayOfWeek() {
        return attendanceDate.getDayOfWeek();
    }

    public boolean isWeekend() {
        return (this.toDayOfWeek().equals(DayOfWeek.SATURDAY) || this.toDayOfWeek().equals(DayOfWeek.SUNDAY));
    }

    public boolean isChristmas() {
        return (this.toLocalDate().equals(CHRISTMAS));
    }

    public boolean isSameDate(AttendanceDate attendanceDate) {
        return attendanceDate.toLocalDate().equals(this.attendanceDate);
    }

    public boolean isMonday() {
        return attendanceDate.getDayOfWeek().equals(DayOfWeek.MONDAY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttendanceDate that = (AttendanceDate) o;
        return Objects.equals(attendanceDate, that.attendanceDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attendanceDate);
    }

    public LocalDate toLocalDate() {
        return attendanceDate;
    }

}
