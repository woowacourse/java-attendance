package attendance;

import java.time.DayOfWeek;
import java.time.LocalDate;

public record AttendanceDate(
        LocalDate date
) {
    public AttendanceDate {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY ||
                date.isEqual(LocalDate.of(2024, 12, 25))) {
            throw new IllegalArgumentException();
        }
    }

    public boolean isEqualToDate(LocalDate date) {
        return this.date.isEqual(date);
    }
}
