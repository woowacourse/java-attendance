import java.time.DayOfWeek;
import java.time.LocalDate;

public class OperationTimeChecker {

    public boolean isWeekend(final LocalDate targetDate) {
        return targetDate.getDayOfWeek() == DayOfWeek.SUNDAY || targetDate.getDayOfWeek() == DayOfWeek.SATURDAY;
    }

    public boolean isHoliday(final LocalDate targetDate) {
        return targetDate.isEqual(LocalDate.of(2024, 12, 25));
    }
}
