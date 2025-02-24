package attendance.common;

import java.time.LocalDate;
import java.util.List;

public final class SystemDateConfig {
    public static final LocalDate START_CALENDER = LocalDate.of(2024, 12, 1);
    public static final LocalDate SYSTEM_TODAY = LocalDate.of(2024, 12, 26);
    public static final List<Integer> DAT_OF_HOLIDAY = List.of(25);

    private SystemDateConfig() {
    }
}
