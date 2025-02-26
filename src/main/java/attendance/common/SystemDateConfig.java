package attendance.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

public final class SystemDateConfig {
    public static final String YEAR_MONTH = "2024-12-";
    public static final LocalDate START_CALENDER = LocalDate.of(2024, Month.DECEMBER, 1);
    public static final LocalDate NOW_DATE = LocalDate.of(2024, Month.DECEMBER, 25);
    public static final LocalDateTime NOW_DATETIME = LocalDateTime.of(2024, Month.DECEMBER, 25, 10, 4);
    public static final List<Integer> DAT_OF_HOLIDAY = List.of(25);

    private SystemDateConfig() {
    }
}
