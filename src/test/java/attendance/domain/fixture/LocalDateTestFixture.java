package attendance.domain.fixture;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.stream.IntStream;

public class LocalDateTestFixture {
    public static LocalDate createRegularDate() {
        LocalDate now = LocalDate.now();
        return IntStream.range(1, now.getMonth().maxLength())
                .mapToObj(day -> LocalDate.of(now.getYear(), now.getMonthValue(), day))
                .filter(date -> !isMonday(date))
                .findFirst()
                .orElseThrow();
    }

    public static LocalDate createMondayDate() {
        LocalDate now = LocalDate.now();
        return IntStream.range(1, now.getMonth().maxLength())
                .mapToObj(day -> LocalDate.of(now.getYear(), now.getMonthValue(), day))
                .filter(date -> isMonday(date))
                .findFirst()
                .orElseThrow();
    }

    private static boolean isMonday(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.MONDAY;
    }
}
