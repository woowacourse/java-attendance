import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class OperationTimeChecker {

    public boolean isWeekend(final LocalDate targetDate) {
        return targetDate.getDayOfWeek() == DayOfWeek.SUNDAY || targetDate.getDayOfWeek() == DayOfWeek.SATURDAY;
    }

    public boolean isHoliday(final LocalDate targetDate) {
        return targetDate.isEqual(LocalDate.of(2024, 12, 25));
    }

    public boolean isContainsOperationTime(final LocalTime targetTime) {
        return true;
    }
}
