package attendance.model;

import java.time.LocalDate;

public record AttendanceDate(LocalDate date) {

    public AttendanceDate(int year, int month, int day) {
        this(LocalDate.of(year, month, day));
    }
}
