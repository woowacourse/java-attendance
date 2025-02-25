package attendance.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public final class SystemDateConfig {
    public static final LocalDate START_CALENDER = LocalDate.of(2024, 12, 1);
    public static final LocalDate SYSTEM_TODAY = LocalDate.now().minusMonths(2);
    public static final LocalDateTime SYSTEM_NOW = LocalDateTime.now().minusMonths(2);
    public static final List<Integer> DAT_OF_HOLIDAY = List.of(25);

    private SystemDateConfig() {
    }
}
