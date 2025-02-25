package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class AttendanceDate {

    private static final List<LocalDate> HOLIDAYS = List.of(LocalDate.of(2024, 12, 25));
    private final LocalDate attendanceDate;

    public AttendanceDate(final LocalDate date) {
        validateCampusOpen(date);
        this.attendanceDate = date;
    }

    private void validateCampusOpen(LocalDate date) {
        if (date.getDayOfWeek().equals(DayOfWeek.SATURDAY)
                || date.getDayOfWeek().equals(DayOfWeek.SUNDAY)
                || HOLIDAYS.contains(date)) {
            throw new IllegalArgumentException("[ERROR] %d월 %d일 %s일은 등교일이 아닙니다.");
        }
    }
}
