package model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Objects;

public class AttendanceDate {
    private final LocalDate attendanceDate;
    private static final LocalDate CHRISTMAS = LocalDate.of(2024, 12, 25);
    private static final LocalDate DECEMBER_START_DATE = LocalDate.of(2024, 12, 1);
    private static final LocalDate DECEMBER_END_DATE = LocalDate.of(2024, 12, 31);

    public AttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public DayOfWeek toDayOfWeek() {
        return attendanceDate.getDayOfWeek();
    }

    public boolean isHoliday() {
        return (isChristmas() || isWeekend());
    }

    public boolean isSameDate(AttendanceDate attendanceDate) {
        return attendanceDate.toLocalDate().equals(this.attendanceDate);
    }

    public boolean isMonday() {
        return attendanceDate.getDayOfWeek().equals(DayOfWeek.MONDAY);
    }

    public boolean isDecemberDay() {
        return (attendanceDate.isAfter(DECEMBER_START_DATE) && attendanceDate.isBefore(DECEMBER_END_DATE));
    }

    public AttendanceDate plusOneDay() {
        return new AttendanceDate(attendanceDate.plusDays(1));
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

    private boolean isWeekend() {
        return (this.toDayOfWeek().equals(DayOfWeek.SATURDAY) || this.toDayOfWeek().equals(DayOfWeek.SUNDAY));
    }

    private boolean isChristmas() {
        return (this.toLocalDate().equals(CHRISTMAS));
    }

    public LocalDate toLocalDate() {
        return attendanceDate;
    }

}
