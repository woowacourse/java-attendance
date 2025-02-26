import java.time.DayOfWeek;
import java.time.LocalDate;

public class OperationTimeChecker {

    public boolean isWeekend(final LocalDate targetDate) {
        if (targetDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return true;
        }
        return false;
    }
}
