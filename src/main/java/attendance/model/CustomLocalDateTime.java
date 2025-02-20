package attendance.model;

import attendance.controller.Parser;
import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class CustomLocalDateTime {
    private static final Clock clock = Clock.fixed(
            LocalDateTime.of(2024, 12, 16, 12, 0).toInstant(ZoneOffset.UTC),
            ZoneId.of("UTC")
    );

    private static final Set<LocalDate> holidayDate = Set.of(LocalDate.of(2024, 12, 25));

    public static LocalDateTime now() {
        return LocalDateTime.now(clock);
    }

    public static LocalDate nowDate() {
        return now().toLocalDate();
    }

    public static LocalTime nowTime() {
        return now().toLocalTime();
    }

    public static LocalTime parseTime(String time) {
        return LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
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
