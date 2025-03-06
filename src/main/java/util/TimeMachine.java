package util;

import java.time.Clock;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneOffset;

public class TimeMachine {
    public static final int FIXED_YEAR = 2024;
    public static final int FIXED_MONTH = 12;
    public static final int FIXED_DAY_OF_MONTH = 13;
    public static final int LAST_DAY_OF_MONTH = YearMonth.of(FIXED_YEAR, FIXED_MONTH).lengthOfMonth();

    private static final ZoneOffset ZONE_OFFSET = ZoneOffset.UTC;
    private static final LocalDate INITIAL_DATE = LocalDate.of(FIXED_YEAR, FIXED_MONTH, FIXED_DAY_OF_MONTH);
    private static Clock clock = Clock.fixed(INITIAL_DATE.atStartOfDay(ZONE_OFFSET).toInstant(), ZONE_OFFSET);

    public static LocalDate dateOfNow() {
        return LocalDate.now(clock);
    }

    public static boolean timeTravelAt(int dayOfMonth) {
        if (!isValidDay(dayOfMonth)) {
            return false;
        }
        LocalDate fixedDate = LocalDate.of(FIXED_YEAR, FIXED_MONTH, dayOfMonth);
        clock = Clock.fixed(fixedDate.atStartOfDay(ZONE_OFFSET).toInstant(), ZONE_OFFSET);
        return true;
    }

    private static boolean isValidDay(int dayOfMonth) {
        if (1 <= dayOfMonth && dayOfMonth <= LAST_DAY_OF_MONTH) {
            return true;
        }

        System.out.printf("%s%d %d월의 올바른 날짜를 입력해주세요. (%d ~ %d)%n%n",
                FormatUtil.ERROR_PREFIX,
                FIXED_YEAR, FIXED_MONTH,
                1, LAST_DAY_OF_MONTH);
        return false;
    }
}
