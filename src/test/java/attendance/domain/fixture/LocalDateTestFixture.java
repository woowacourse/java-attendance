package attendance.domain.fixture;

import attendance.config.FixedLocalDateProvider;
import attendance.domain.Holiday;
import attendance.domain.LocalDateProvider;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LocalDateTestFixture {
    public static final LocalDateProvider DATE_PROVIDER;
    public static final LocalDate TEST_DATE;

    static {
        TEST_DATE = LocalDate.of(2024, 12, 31);
        DATE_PROVIDER = new FixedLocalDateProvider(TEST_DATE);
    }

    public static LocalDate createRegularDate() {
        LocalDate now = DATE_PROVIDER.now();
        return IntStream.range(1, now.getMonth().maxLength())
                .mapToObj(day -> LocalDate.of(now.getYear(), now.getMonthValue(), day))
                .filter(date -> !isMonday(date))
                .filter(date -> !isWeekend(date))
                .findFirst()
                .orElseThrow();
    }

    public static List<LocalDate> createRegularDates(int endDate) {
        LocalDate now = DATE_PROVIDER.now();
        return IntStream.range(1, endDate)
                .mapToObj(day -> LocalDate.of(now.getYear(), now.getMonthValue(), day))
                .filter(date -> !Holiday.isHoliday(date))
                .filter(date -> !isWeekend(date))
                .collect(Collectors.toList());
    }

    public static LocalDate createMondayDate() {
        LocalDate now = DATE_PROVIDER.now();
        return IntStream.range(1, now.getMonth().maxLength())
                .mapToObj(day -> LocalDate.of(now.getYear(), now.getMonthValue(), day))
                .filter(LocalDateTestFixture::isMonday)
                .findFirst()
                .orElseThrow();
    }

    public static LocalDate createWeekendDate() {
        LocalDate now = DATE_PROVIDER.now();
        return IntStream.range(1, now.getMonth().maxLength())
                .mapToObj(day -> LocalDate.of(now.getYear(), now.getMonthValue(), day))
                .filter(LocalDateTestFixture::isWeekend)
                .findFirst()
                .orElseThrow();
    }

    private static boolean isMonday(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.MONDAY;
    }

    private static boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SUNDAY || date.getDayOfWeek() == DayOfWeek.SATURDAY;
    }

}
