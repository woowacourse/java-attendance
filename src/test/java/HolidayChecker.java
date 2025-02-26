import java.time.LocalDate;

public class HolidayChecker {
    public boolean isHoliday(final LocalDate targetDate) {
        return targetDate.isEqual(LocalDate.of(2024, 12, 25));
    }
}
