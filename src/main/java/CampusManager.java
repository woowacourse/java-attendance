import java.time.DayOfWeek;
import java.time.LocalDate;

public class CampusManager {
    private static final LocalDate CHRISTMAS = LocalDate.of(2024, 12, 25);

    public boolean isOperationDate(final LocalDate date) {
        return !(isHoliday(date) || isWeekend(date));
    }

    private boolean isHoliday(final LocalDate date) {
        return date.isEqual(CHRISTMAS);
    }

    private static boolean isWeekend(final LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day.equals(DayOfWeek.SATURDAY) ||
                day.equals(DayOfWeek.SUNDAY);
    }
}
