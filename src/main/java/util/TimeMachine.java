package util;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneOffset;

public class TimeMachine {
    public static final int FIXED_YEAR = 2024;
    public static final int FIXED_MONTH = 12;
    public static final int FIXED_DAY_OF_MONTH = 13;

    private static final ZoneOffset ZONE_OFFSET = ZoneOffset.UTC;
    private static final LocalDate INITIAL_DATE = LocalDate.of(FIXED_YEAR, FIXED_MONTH, FIXED_DAY_OF_MONTH);
    private static Clock clock = Clock.fixed(INITIAL_DATE.atStartOfDay(ZONE_OFFSET).toInstant(), ZONE_OFFSET);

    public static LocalDate dateOfNow() {
        return LocalDate.now(clock);
    }

    public static void timeTravelAt(int dayOfMonth) {
        LocalDate fixedDate = LocalDate.of(FIXED_YEAR, FIXED_MONTH, dayOfMonth);
        clock = Clock.fixed(fixedDate.atStartOfDay(ZONE_OFFSET).toInstant(), ZONE_OFFSET);
    }
}
