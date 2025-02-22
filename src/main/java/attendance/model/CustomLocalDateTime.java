package attendance.model;

import attendance.controller.Parser;
import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Set;

public class CustomLocalDateTime {
    public static final LocalDateTime FIXED_DATE_TIME = LocalDateTime.of(2024, 12, 16, 12, 0);
    private static final Clock clock = Clock.fixed(
            FIXED_DATE_TIME.toInstant(ZoneOffset.UTC),
            ZoneId.of("UTC")
    );
    private static final Set<LocalDate> holidayDate = Set.of(LocalDate.of(2024, 12, 25));

    public static LocalDateTime now() {
        return LocalDateTime.now(clock);
    }

    public static LocalDate nowDate() {
        return now().toLocalDate();
    }

    public static LocalDate parseDate(String day) {
        return LocalDate.of(2024, 12, Parser.parseInt(day));
    }

    public static boolean isHoliday(LocalDate localDate) {
        return localDate.getDayOfWeek().equals(DayOfWeek.SATURDAY) ||
                localDate.getDayOfWeek().equals(DayOfWeek.SUNDAY) ||
                holidayDate.contains(localDate);
    }
}
