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
        if (targetTime.isBefore(LocalTime.of(8, 0))) {
            return false;
        }
        if (targetTime.isAfter(LocalTime.of(23, 0))) {
            return false;
        }
        return true;
    }
}
