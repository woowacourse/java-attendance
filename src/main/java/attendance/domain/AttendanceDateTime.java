package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import attendance.interfaces.SystemDateTime;

public class AttendanceDateTime implements SystemDateTime {
    private static final LocalDate CAMPUS_START_DAY = LocalDate.of(2024, Month.DECEMBER, 1);
    private static final LocalDateTime NOW_DATETIME
        = LocalDateTime.of(2024, Month.DECEMBER, 26, 10, 4);

    private static final List<Integer> DAT_OF_HOLIDAY = List.of(25);

    @Override
    public LocalDateTime now() {
        return NOW_DATETIME;
    }

    @Override
    public LocalDate nowDate() {
        return NOW_DATETIME.toLocalDate();
    }

    @Override
    public List<LocalDate> extractWorkingDays() {
        return Stream.iterate(CAMPUS_START_DAY, date -> date.plusDays(1))
            .limit(ChronoUnit.DAYS.between(CAMPUS_START_DAY, NOW_DATETIME))
            .filter(this::isWorkingDay)
            .collect(Collectors.toList());
    }

    @Override
    public boolean isWorkingDay(LocalDate date) {
        return !isWeekend(date) && !isHoliday(date);
    }

    private static boolean isHoliday(LocalDate date) {
        return DAT_OF_HOLIDAY.stream()
            .anyMatch(day -> date.getDayOfMonth() == day);
    }

    private static boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek().getValue() >= DayOfWeek.SATURDAY.getValue();
    }
}
