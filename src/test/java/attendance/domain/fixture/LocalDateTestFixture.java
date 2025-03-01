package attendance.domain.fixture;

import attendance.domain.AttendanceChecker;
import attendance.domain.Holiday;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LocalDateTestFixture {
    public static LocalDate createRegularDate() {
        LocalDate now = LocalDate.now();
        return IntStream.range(1, now.getMonth().maxLength())
                .mapToObj(day -> LocalDate.of(now.getYear(), now.getMonthValue(), day))
                .filter(date -> !isMonday(date))
                .filter(date -> !isWeekend(date))
                .findFirst()
                .orElseThrow();
    }

    public static List<LocalDate> createRegularDates(int endDate) {
        LocalDate now = LocalDate.now();
        return IntStream.range(1, endDate)
                .mapToObj(day -> LocalDate.of(now.getYear(), now.getMonthValue(), day))
                .filter(date -> !Holiday.isHoliday(date))
                .filter(date -> !isWeekend(date))
                .collect(Collectors.toList());
    }

    public static LocalDate createMondayDate() {
        LocalDate now = LocalDate.now();
        return IntStream.range(1, now.getMonth().maxLength())
                .mapToObj(day -> LocalDate.of(now.getYear(), now.getMonthValue(), day))
                .filter(LocalDateTestFixture::isMonday)
                .findFirst()
                .orElseThrow();
    }

    public static LocalDate createWeekendDate() {
        LocalDate now = LocalDate.now();
        return IntStream.range(1, now.getMonth().maxLength())
                .mapToObj(day -> LocalDate.of(now.getYear(), now.getMonthValue(), day))
                .filter(LocalDateTestFixture::isWeekend)
                .findFirst()
                .orElseThrow();
    }

    public static int countOnCampusDay(int today) {
        LocalDate now = LocalDate.now();
        return (int) IntStream.range(1, today)
                .mapToObj(day -> LocalDate.of(now.getYear(), now.getMonthValue(), day))
                .filter(AttendanceChecker::isCampusOpenDate)
                .count();
    }

    private static boolean isMonday(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.MONDAY;
    }

    private static boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SUNDAY || date.getDayOfWeek() == DayOfWeek.SATURDAY;
    }
}
