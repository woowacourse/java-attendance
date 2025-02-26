import java.time.DayOfWeek;
import java.time.LocalDate;

public class OperationTimeChecker {

    public boolean isWeekend(final LocalDate targetDate) {
        return targetDate.getDayOfWeek() == DayOfWeek.SUNDAY || targetDate.getDayOfWeek() == DayOfWeek.SATURDAY;
    }
}
