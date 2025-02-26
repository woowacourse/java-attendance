package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AttendanceDate {

    private static final List<LocalDate> HOLIDAYS = List.of(LocalDate.of(2024, 12, 25));
    private final LocalDate attendanceDate;

    public AttendanceDate(final LocalDate date) {
        validateCampusOpen(date);
        this.attendanceDate = date;
    }

    public LocalTime getEducationStartTime() {
        if (attendanceDate.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
            return LocalTime.of(13, 0);
        }
        return LocalTime.of(10, 0);
    }

    private void validateCampusOpen(LocalDate date) {
        if (date.getDayOfWeek().equals(DayOfWeek.SATURDAY)
                || date.getDayOfWeek().equals(DayOfWeek.SUNDAY)
                || HOLIDAYS.contains(date)) {
            throw new IllegalArgumentException("[ERROR] %d월 %d일 %s일은 등교일이 아닙니다.");
        }
    }

    public boolean isSameAs(LocalDate date) {
        return this.attendanceDate.equals(date);
    }

    public boolean isSameAs(AttendanceDate date) {
        return this.attendanceDate.equals(date.attendanceDate);
    }

    public int getMonthValue() {
        return this.attendanceDate.getMonthValue();
    }

    public int getDayOfMonth() {
        return this.attendanceDate.getDayOfMonth();
    }

    public DayOfWeek getDayOfWeek() {
        return this.attendanceDate.getDayOfWeek();
    }
}
