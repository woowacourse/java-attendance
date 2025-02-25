package attendance;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class AttendanceDate {
    private final LocalDate date;

    public AttendanceDate(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY ||
                date.isEqual(LocalDate.of(2024, 12, 25))) {
            throw new IllegalArgumentException();
        }
        this.date = date;
    }

    public boolean isEqualToDate(LocalDate date) {
        return this.date.isEqual(date);
    }
}
